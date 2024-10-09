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

All URIs are relative to *https://api.example.com/v1*

Class | Method | HTTP request | Description
------------ | ------------- | ------------- | -------------
*UsersApi* | [**createUser**](docs/UsersApi.md#createuser) | **POST** /users | Create a new user
*UsersApi* | [**getUserById**](docs/UsersApi.md#getuserbyid) | **GET** /users/{userId} | Get a user by ID
*UsersApi* | [**getUsers**](docs/UsersApi.md#getusers) | **GET** /users | Get all users


<a id="documentation-for-models"></a>
## Documentation for Models

 - [com.polo.api.model.CreateUserRequest](docs/CreateUserRequest.md)
 - [com.polo.api.model.User](docs/User.md)


<a id="documentation-for-authorization"></a>
## Documentation for Authorization

Endpoints do not require authorization.

