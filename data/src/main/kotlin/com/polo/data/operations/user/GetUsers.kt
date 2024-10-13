package com.polo.data.operations.user

import com.polo.data.bindings.tables.references.USERS
import com.polo.data.operations.ReadOperation
import com.polo.data.operations.ReadOperationContext

class GetUsers(dataVersion: Long? = null): ReadOperation<List<User>>(dataVersion) {
    override fun read(readOperationContext: ReadOperationContext): List<User> {
        val dsl = readOperationContext.dslContext
        return dsl.selectVersioned(USERS, readOperationContext)
            .fetch()
            .map { userRecord ->
                User(
                    firstName = userRecord.firstName!!,
                    lastName = userRecord.lastName!!,
                    email = userRecord.email!!,
                    phoneNumber = userRecord.phoneNumber!!
                )
            }
    }
}
