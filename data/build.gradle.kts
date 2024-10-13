plugins {
    alias(libs.plugins.flyway)
    alias(libs.plugins.jooq)
}

dependencies {
    jooqGenerator(libs.postgresql)

    implementation(libs.coroutines.core)
    implementation(libs.flyway.core)
    implementation(libs.jooq)
    implementation(libs.jooq.kotlin)
    implementation(libs.jooq.meta)
    implementation(libs.jooq.codegen)
    implementation(libs.hikaricp)
    implementation(libs.postgresql)

    implementation(project(":net"))
}

flyway {
    url = "jdbc:postgresql://localhost:5432/polo"
    user = "user"
    password = "password"
    locations = arrayOf("filesystem:src/main/resources/db/migration")
}

jooq {
    version.set("3.18.2") // Your JOOQ version
    edition.set(nu.studer.gradle.jooq.JooqEdition.OSS) // Use OSS edition

    configurations {
        create("main") { // Configuration name
            generateSchemaSourceOnCompilation.set(true) // Enable schema source generation
            jooqConfiguration.apply {
                jdbc.apply {
                    driver = "org.postgresql.Driver"
                    url = "jdbc:postgresql://localhost:5432/polo"
                    user = "user"
                    password = "password"
                }

                generator.apply {
                    name = "org.jooq.codegen.KotlinGenerator"
                    strategy.name = "org.jooq.codegen.DefaultGeneratorStrategy"

                    database.apply {
                        name = "org.jooq.meta.postgres.PostgresDatabase"
                        inputSchema = "public"
                    }

                    generate.apply {
                        isJavaTimeTypes = true
                    }

                    target.apply {
                        packageName = "com.polo.data.bindings"
                    }
                }
            }
        }
    }
}

tasks {
    named("compileKotlin") {
        dependsOn("generateJooq")
    }

    named("flywayMigrate") {
        dependsOn(rootProject.tasks.named("composeUp"))
    }

    named("generateJooq") {
        dependsOn("flywayMigrate")
    }
}
