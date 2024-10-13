package com.polo.data.operations

import com.polo.data.bindings.tables.records.TransactionsRecord
import org.jooq.DSLContext

class WriteOperationContext(
    val dslContext: DSLContext,
    val transaction: TransactionsRecord
)
