package com.polo.data.operations

import com.polo.core.BackoffRetry
import com.polo.data.bindings.tables.records.TransactionsRecord
import com.polo.data.bindings.tables.references.TRANSACTIONS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jooq.DSLContext
import org.jooq.exception.DataAccessException
import org.jooq.exception.IOException
import org.jooq.impl.DSL
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.sql.SQLTimeoutException

abstract class WriteOperation<T>: DatabaseOperation<T> {
    protected abstract fun write(writeOperationContext: WriteOperationContext): T

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
                dslContext.transactionResult { configuration ->
                    val ctx = DSL.using(configuration)
                    val tx: TransactionsRecord = insertTransaction(ctx) ?: throw RuntimeException("Failed to create transaction")
                    // Execute the abstract write operation
                    val result = write(WriteOperationContext(ctx, tx))
                    result
                }
            }
        }
    }

    // Move the insert operation into a separate function to narrow the blocking scope
    private fun insertTransaction(ctx: DSLContext): TransactionsRecord? {
        return ctx.insertInto(TRANSACTIONS)
            .defaultValues()
            .returning()
            .fetchOne() // Blocking I/O handled here
    }
}