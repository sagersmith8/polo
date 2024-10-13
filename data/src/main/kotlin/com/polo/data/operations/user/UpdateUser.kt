package com.polo.data.operations.user

import com.polo.data.bindings.tables.references.TRANSACTIONS
import com.polo.data.bindings.tables.references.USERS
import com.polo.data.operations.WriteOperationContext
import com.polo.data.operations.WriteOperation

class UpdateUser(
    private val user: User): WriteOperation<User>() {

        override fun write(writeOperationContext: WriteOperationContext): User {
            val dsl = writeOperationContext.dslContext
            val transactionId = writeOperationContext.transaction.getValue(TRANSACTIONS.TRANSACTION_ID)

            // Mark the existing user as ended
            val oldUser = dsl.update(USERS)
                .set(USERS.END_TX, transactionId)
                .where(USERS.EMAIL.eq(user.email))
                    .and(USERS.END_TX.isNull)
                .returning()
                .fetchOne() ?: throw RuntimeException("Failed to update user with phone number")

            // Insert the new user record
            val newUser = dsl.insertInto(USERS)
                .set(USERS.EMAIL, user.email)
                .set(USERS.FIRST_NAME, user.firstName)
                .set(USERS.LAST_NAME, user.lastName)
                .set(USERS.PHONE_NUMBER, user.phoneNumber)
                .set(USERS.BEGIN_TX, transactionId)
                .set(USERS.CREATED_TX, oldUser.getValue(USERS.CREATED_TX))
                .returning()
                .fetchOne() ?: throw RuntimeException("Failed to insert new user with phone number")

            return User(
                firstName = newUser.firstName!!,
                lastName = newUser.lastName!!,
                email = newUser.email!!,
                phoneNumber = newUser.phoneNumber!!,
            )
        }
    }
