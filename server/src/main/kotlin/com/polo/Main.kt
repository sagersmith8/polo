package com.polo

import com.polo.api.JerseyApp
import com.polo.api.UsersApiImpl
import com.polo.data.DatabaseManager
import com.polo.data.DatabaseManagerImpl
import com.polo.data.FlywayMigrationManagerImpl
import jakarta.ws.rs.ApplicationPath
import org.eclipse.jetty.ee10.servlet.ServletContextHandler
import org.eclipse.jetty.server.Server
import org.glassfish.jersey.servlet.ServletContainer
import org.glassfish.jersey.servlet.ServletProperties

fun main() {
    val databaseManager: DatabaseManager = DatabaseManagerImpl()

    // Set up Jetty server
    val server = Server(8080)  // Define the port
    val context = ServletContextHandler(ServletContextHandler.NO_SESSIONS)

    context.contextPath = "/"
    server.handler = context

    context
        .addServlet(ServletContainer::class.java, "/*")
        .setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyApp::class.java.name)

    // Start the server
    server.start()
    server.join()
}
