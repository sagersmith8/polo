/**
 * User API
 * API for creating and retrieving users
 *
 * The version of the OpenAPI document: 1.0.0
 * 
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
*/
package com.polo.api.model

import jakarta.validation.constraints.*
import jakarta.validation.Valid
import com.fasterxml.jackson.annotation.JsonProperty
/**
 * 
 *
 * @param firstName 
 * @param lastName 
 * @param phone 
 * @param email 
 * @param password 
 */


data class CreateUserRequest (


    @JsonProperty("first_name")
    @field:Valid
    @field:NotNull
 @field:Pattern(regexp="^[A-Za-z]{1,50}$")    val firstName: kotlin.String,


    @JsonProperty("last_name")
    @field:Valid
    @field:NotNull
 @field:Pattern(regexp="^[A-Za-z]{1,50}$")    val lastName: kotlin.String,


    @JsonProperty("phone")
    @field:Valid
    @field:NotNull
 @field:Pattern(regexp="^\\+?[1-9]\\d{1,14}$")    val phone: kotlin.String,


    @JsonProperty("email")
    @field:Valid
    @field:NotNull
 @field:Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")    val email: kotlin.String,


    @JsonProperty("password")
    @field:Valid
    @field:NotNull
    val password: kotlin.String

)

