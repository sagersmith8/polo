package com.polo.data.operations.user

import com.polo.data.bindings.tables.references.USERS
import com.polo.data.operations.ReadOperation
import com.polo.data.operations.ReadOperationContext

class GetUser(
    private val email: String,
    dataVersion: Long? = null
    ): ReadOperation<User>(dataVersion) {

    override fun read(readOperationContext: ReadOperationContext): User {
        val dsl = readOperationContext.dslContext
        val dataVersion = readOperationContext.dataVersion
        return dsl.selectFrom(USERS.atDataVersion(dataVersion))
            .where(USERS.EMAIL.eq(email))
            .fetchOneInto(USERS)
            ?.let { user ->
                User(
                    firstName = user.firstName!!,
                    lastName = user.lastName!!,
                    email = user.email!!,
                    phoneNumber = user.phoneNumber!!
                )
            } ?: throw RuntimeException("User not found for email")
    }
}