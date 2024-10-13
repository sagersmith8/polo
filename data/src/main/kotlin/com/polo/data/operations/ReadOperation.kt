package com.polo.data.operations

import com.polo.net.BackoffRetry
import com.polo.data.bindings.tables.references.TRANSACTIONS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.*
import org.jooq.exception.DataAccessException
import org.jooq.exception.IOException
import org.jooq.impl.DSL
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

    fun <R : Record> DSLContext.selectVersioned(
        table: Table<R>,
        readOperationContext: ReadOperationContext
    ): SelectConditionStep<R> {
        // Assuming all tables follow the convention of BEGIN_TX and END_TX columns.
        val beginField = table.field("BEGIN_TX", Long::class.java)!!
        val endField = table.field("END_TX", Long::class.java)!!

        return if (readOperationContext.dataVersion == null) {
            // Fetch the active/current version
            selectFrom(table).where(endField.isNull)
        } else {
            // Ensure the transaction exists and filter by data version
            selectFrom(table).where(
                DSL.exists(
                    selectOne()
                        .from(TRANSACTIONS)
                        .where(TRANSACTIONS.TRANSACTION_ID.eq(readOperationContext.dataVersion))
                )
                    .and(beginField.le(readOperationContext.dataVersion))
                    .and(endField.isNull.or(endField.ge(readOperationContext.dataVersion)))
            )
        }
    }
}
