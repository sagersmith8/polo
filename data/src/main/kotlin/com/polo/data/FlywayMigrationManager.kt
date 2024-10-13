package com.polo.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.flywaydb.core.Flyway
import javax.sql.DataSource

interface FlywayMigrationManager {
    fun migrateSynchronously()
    suspend fun migrateAsynchronously()
}

class FlywayMigrationManagerImpl(dataSource: DataSource): FlywayMigrationManager {
    private val flyway: Flyway = Flyway.configure()
        .dataSource(dataSource)
        .load()

    override fun migrateSynchronously() {
        flyway.migrate()
    }

    override suspend fun migrateAsynchronously() {
        withContext(Dispatchers.IO) {
            flyway.migrate()
        }
    }
}