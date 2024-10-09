plugins {
    alias(libs.plugins.open.api.generator)
}

openApiGenerate {
    generatorName.set("kotlin-server")  // Use JAX-RS spec generator
    library.set("jaxrs-spec")
    inputSpec.set("$rootDir/api/openapi.yaml")  // Path to your OpenAPI spec
    outputDir.set("$projectDir")

    apiPackage.set("com.polo.api")  // Package for the API interfaces
    modelPackage.set("com.polo.api.model")  // Package for model classes

    configOptions.put("useTags", "true")  // Optional: Split API into different files per tag
    configOptions.put("useBeanValidation", "true")
    configOptions.put("dateLibrary", "java8")
    configOptions.put("interfaceOnly", "true")
    configOptions.put("useJakartaEe", "true")
}

dependencies {
    api(libs.jackson.datatype.jsr310)
    api(libs.jackson.jaxrs.json.provider)

    implementation(libs.jackson.annotations)
    implementation(libs.jakarta.annotation.api)
    implementation(libs.jakarta.ws.rs.api)
    implementation(libs.jakarta.validation.api)
}
