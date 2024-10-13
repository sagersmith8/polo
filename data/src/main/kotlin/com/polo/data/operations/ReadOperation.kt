package com.polo.data.operations

import com.polo.core.BackoffRetry
import com.polo.data.bindings.tables.references.TRANSACTIONS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.*
import org.jooq.exception.DataAccessException
import org.jooq.exception.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.sql.SQLTimeoutException

abstract class ReadOperation<T>(
    private val dataVersion: Long?
): DatabaseOperation<T> {
    protected abstract fun read(readOperationContext: ReadOperationContext): T

    private val retry: BackoffRetry = BackoffRetry(
        factor = 2.0,
        initialDelay = 1000,
        maxRetries = 3,
        retryableExceptions = setOf(
            DataAccessException::class.java,
            IOException::class.java,
            SocketTimeoutException::class.java,
            SQLTimeoutException::class.java,
            UnknownHostException::class.java
        )
    )

    override suspend fun execute(dslContext: DSLContext): T {
        return withContext(Dispatchers.IO) {
            retry.retry {
                read(ReadOperationContext(dslContext, dataVersion))
            }
        }
    }

    fun <R : Record> Table<R>.atDataVersion(
        dataVersion: Long?
    ): Table<out Record> {
        // Assuming BEGIN_TX and END_TX are always present
        val beginField = checkNotNull(this.field("BEGIN_TX", Long::class.java)) {
            "Field 'BEGIN_TX' not found in table ${this.name}"
        }
        val endField = checkNotNull(this.field("END_TX", Long::class.java)) {
            "Field 'END_TX' not found in table ${this.name}"
        }

        return if (dataVersion == null) {
            this // Return the table itself if no versioning is needed
        } else {
            this
                .join(TRANSACTIONS)
                .on(TRANSACTIONS.TRANSACTION_ID.eq(dataVersion))
                .and(beginField.le(dataVersion)) // Start before or at the transaction ID
                .and(endField.isNull.or(endField.ge(dataVersion))) // Still valid or ends after the transaction
        }
    }

}