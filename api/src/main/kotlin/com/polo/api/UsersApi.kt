package com.polo.api;

import com.polo.api.model.CreateUserRequest
import com.polo.api.model.User

import jakarta.ws.rs.*
import jakarta.ws.rs.core.Response


import java.io.InputStream
import jakarta.validation.constraints.*
import jakarta.validation.Valid


@Path("/")
@jakarta.annotation.Generated(value = arrayOf("org.openapitools.codegen.languages.KotlinServerCodegen"), comments = "Generator version: 7.9.0")
interface UsersApi {

    @POST
    @Path("/users")
    @Consumes("application/json")
    @Produces("application/json")
    fun createUser(@Valid @NotNull  createUserRequest: CreateUserRequest): User

    @GET
    @Path("/users/{userId}")
    @Produces("application/json")
    fun getUserById(@PathParam("userId") userId: kotlin.String): User

    @GET
    @Path("/users")
    @Produces("application/json")
    fun getUsers(): kotlin.collections.List<User>
}
