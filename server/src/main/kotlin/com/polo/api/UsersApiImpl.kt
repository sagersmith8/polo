package com.polo.api

import com.polo.api.model.CreateUserRequest
import com.polo.api.model.User
import com.polo.data.DatabaseManager
import com.polo.data.operations.user.CreateUser

class UsersApiImpl(private val databaseManager: DatabaseManager): UsersApi {
    override suspend fun createUser(createUserRequest: CreateUserRequest): User {
        val user = databaseManager.execute(
            CreateUser(
                createUserRequest.phone
            )
        )
        return User(
            id = "",
            name = "",
            phone = user.phoneNumber,
        )
    }

    override suspend fun getUserById(userId: String): User {
        TODO("Not yet implemented")
    }

    override suspend fun getUsers(): List<User> {
        TODO("Not yet implemented")
    }

}
