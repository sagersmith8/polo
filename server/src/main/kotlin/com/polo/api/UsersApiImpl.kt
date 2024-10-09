package com.polo.api

import com.polo.api.UsersApi
import com.polo.api.model.CreateUserRequest
import com.polo.api.model.User

class UsersApiImpl: UsersApi {
    override fun createUser(createUserRequest: CreateUserRequest): User {
        TODO("Not yet implemented")
    }

    override fun getUserById(userId: String): User {
        TODO("Not yet implemented")
    }

    override fun getUsers(): List<User> {
        TODO("Not yet implemented")
    }

}
