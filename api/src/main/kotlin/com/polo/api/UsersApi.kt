package com.polo.api;

import com.polo.api.model.JsonPatchInner
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
    suspend fun createUser(@Valid @NotNull  user: User): User

    @DELETE
    @Path("/users/{email}")
    suspend fun deleteUser(@PathParam("email") @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") email: kotlin.String)

    @GET
    @Path("/users/{email}")
    @Produces("application/json")
    suspend fun getUser(@PathParam("email") @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") email: kotlin.String): User

    @GET
    @Path("/users")
    @Produces("application/json")
    suspend fun getUsers(): kotlin.collections.List<User>

    @PATCH
    @Path("/users/{email}")
    @Consumes("application/json-patch+json")
    @Produces("application/json")
    suspend fun updateUser(@PathParam("email") @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") email: kotlin.String,@Valid @NotNull  jsonPatchInner: kotlin.collections.List<JsonPatchInner>): User
}
