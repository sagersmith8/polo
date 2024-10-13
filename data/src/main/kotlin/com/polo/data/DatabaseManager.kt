package com.polo.data

import com.polo.data.operations.DatabaseOperation
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jooq.impl.DSL

interface DatabaseManager: AutoCloseable {
    suspend fun <T>execute(databaseOperation: DatabaseOperation<T>): T
}

class DatabaseManagerImpl: DatabaseManager {
    private val dataSource = HikariDataSource(
       HikariConfig().apply {
            driverClassName = "org.postgresql.Driver"
            jdbcUrl = ""
            maximumPoolSize = 1
            password = ""
            username = ""
        }
    )

    private val job = CoroutineScope(Dispatchers.IO).launch {
        FlywayMigrationManagerImpl(dataSource).migrateAsynchronously()
    }

    override suspend fun <T> execute(databaseOperation: DatabaseOperation<T>): T {
        return databaseOperation.execute(DSL.using(dataSource.connection))
    }

    override fun close() {
        job.cancel()
        dataSource.close()
    }
}