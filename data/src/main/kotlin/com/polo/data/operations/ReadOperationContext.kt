package com.polo.data.operations

import org.jooq.DSLContext

data class ReadOperationContext(
    val dslContext: DSLContext,
    val dataVersion: Long?
)
