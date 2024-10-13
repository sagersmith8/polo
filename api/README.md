# org.openapitools.server - Kotlin Server library for User API

## Requires

* Kotlin 1.4.31
* Gradle 6.8.2

## Build

First, create the gradle wrapper script:

```
gradle wrapper
```

Then, run:

```
./gradlew check assemble
```

This runs all tests and packages the library.

## Features/Implementation Notes

* Supports JSON inputs/outputs, File inputs, and Form inputs.
* Supports collection formats for query parameters: csv, tsv, ssv, pipes.
* Some Kotlin and Java types are fully qualified to avoid conflicts with types defined in OpenAPI definitions.

<a id="documentation-for-api-endpoints"></a>
## Documentation for API Endpoints

All URIs are relative to *https://localhost:8080*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*UsersApi* | [**createUser**](docs/UsersApi.md#createuser) | **POST** /users | Create a new user
*UsersApi* | [**deleteUser**](docs/UsersApi.md#deleteuser) | **DELETE** /users/{email} | Delete a user by email
*UsersApi* | [**getUser**](docs/UsersApi.md#getuser) | **GET** /users/{email} | Get a user by email
*UsersApi* | [**getUsers**](docs/UsersApi.md#getusers) | **GET** /users | Get all users
*UsersApi* | [**updateUser**](docs/UsersApi.md#updateuser) | **PATCH** /users/{email} | Update a user by Email


<a id="documentation-for-models"></a>
## Documentation for Models

 - [com.polo.api.model.JsonPatchInner](docs/JsonPatchInner.md)
 - [com.polo.api.model.JsonPatchInnerOneOf](docs/JsonPatchInnerOneOf.md)
 - [com.polo.api.model.JsonPatchInnerOneOf1](docs/JsonPatchInnerOneOf1.md)
 - [com.polo.api.model.JsonPatchInnerOneOf2](docs/JsonPatchInnerOneOf2.md)
 - [com.polo.api.model.User](docs/User.md)


<a id="documentation-for-authorization"></a>
## Documentation for Authorization

Endpoints do not require authorization.

