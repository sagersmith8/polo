package com.polo.api

import jakarta.ws.rs.ApplicationPath
import org.glassfish.jersey.server.ResourceConfig

@ApplicationPath("/")
class JerseyApp : ResourceConfig() {
    init {
        register(UsersApiImpl::class.java)
    }
}
