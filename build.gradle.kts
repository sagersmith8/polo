plugins {
    idea
    jacoco
    kotlin("jvm") version "1.9.25"
}

allprojects {
    apply(plugin = "idea")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "jacoco")

    version = "0.0.1"

    repositories {
        mavenCentral()
    }

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        register<Exec>("composeUp") {
            commandLine = listOf("./docker/integration-env-setup.sh")
        }

        register<Exec>("composeDown") {
            commandLine = listOf("./docker/integration-env-teardown.sh")
        }
    }
}
