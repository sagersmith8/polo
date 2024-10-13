package com.polo.data.operations.user

import com.polo.data.bindings.tables.references.TRANSACTIONS
import com.polo.data.bindings.tables.references.USERS
import com.polo.data.operations.WriteOperationContext
import com.polo.data.operations.WriteOperation

class DeleteUser(
    private val email: String): WriteOperation<User>() {
    override fun write(writeOperationContext: WriteOperationContext): User {
        val dsl = writeOperationContext.dslContext
        val transactionId = writeOperationContext.transaction.getValue(TRANSACTIONS.TRANSACTION_ID)
        val deletedUser = dsl.update(USERS)
            .set(USERS.END_TX, transactionId)
            .where(USERS.EMAIL.eq(email))
                .and(USERS.END_TX.isNull)
            .returning()
            .fetchOne() ?: throw RuntimeException("Failed to mark user as deleted")
        return User(
            firstName = deletedUser.firstName!!,
            lastName = deletedUser.lastName!!,
            email = deletedUser.email!!,
            phoneNumber = deletedUser.phoneNumber!!,
        )
    }
}