package com.polo.api

import com.polo.api.model.JsonPatchInner
import com.polo.api.model.User
import com.polo.data.DatabaseManager
import com.polo.data.operations.user.*
import com.polo.net.rest.Patch

class UsersApiImpl(private val databaseManager: DatabaseManager): UsersApi {
    override suspend fun createUser(user: User): User {
        return databaseManager.execute(
            CreateUser(
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                phoneNumber = user.phone
            )
        ).let { createdUser ->
            User(
                firstName = createdUser.firstName,
                lastName = createdUser.lastName,
                email = createdUser.email,
                phone = createdUser.phoneNumber,
            )
        }
    }

    override suspend fun deleteUser(email: String) {
        databaseManager.execute(
            DeleteUser(email)
        )
    }

    override suspend fun getUser(email: String): User {
        return databaseManager.execute(
            GetUser(email)
        ).let { fetchedUser ->
            User(
                firstName = fetchedUser.firstName,
                lastName = fetchedUser.lastName,
                email = fetchedUser.email,
                phone = fetchedUser.phoneNumber,
            )
        }
    }

    override suspend fun getUsers(): List<User> {
        return databaseManager.execute(
            GetUsers()
        ).map { fetchedUser ->
            User(
                firstName = fetchedUser.firstName,
                lastName = fetchedUser.lastName,
                email = fetchedUser.email,
                phone = fetchedUser.phoneNumber,
            )
        }
    }

    override suspend fun updateUser(email: String, jsonPatchInner: List<JsonPatchInner>): User {
        val currentUser = databaseManager.execute(
            GetUser(email)
        )

        val updatedUser = Patch.apply(currentUser, jsonPatchInner)

        return databaseManager.execute(
            UpdateUser(
                updatedUser
            )
        ).let { updateUser ->
            User(
                firstName = updateUser.firstName,
                lastName = updateUser.lastName,
                email = updateUser.email,
                phone = updateUser.phoneNumber,
            )
        }
    }
}
