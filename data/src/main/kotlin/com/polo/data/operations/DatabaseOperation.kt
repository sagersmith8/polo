package com.polo.data.operations

import org.jooq.DSLContext

interface DatabaseOperation<T> {
    suspend fun execute(dslContext: DSLContext): T
}