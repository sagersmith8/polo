package com.polo.data.operations.user

import com.polo.data.bindings.tables.references.TRANSACTIONS
import com.polo.data.bindings.tables.references.USERS
import com.polo.data.operations.WriteOperationContext
import com.polo.data.operations.WriteOperation

class CreateUser(
    private val firstName: String,
    private val lastName: String,
    private val email: String,
    private val phoneNumber: String
    ): WriteOperation<User>() {
    override fun write(writeOperationContext: WriteOperationContext): User {
        val dsl = writeOperationContext.dslContext
        val transactionId = writeOperationContext.transaction.getValue(TRANSACTIONS.TRANSACTION_ID)

        val usersRecord = dsl.insertInto(USERS)
            .set(USERS.FIRST_NAME, firstName)
            .set(USERS.LAST_NAME, lastName)
            .set(USERS.EMAIL, email)
            .set(USERS.PHONE_NUMBER, phoneNumber)
            .set(USERS.CREATED_TX, transactionId)
            .set(USERS.BEGIN_TX, transactionId)
            .returning()
            .fetchOne() ?: throw RuntimeException("Failed to insert or update user")

        return User(
            firstName = usersRecord.firstName!!,
            lastName = usersRecord.lastName!!,
            phoneNumber = usersRecord.phoneNumber!!,
            email = usersRecord.email!!,
        )
    }

}