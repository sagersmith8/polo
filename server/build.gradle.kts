dependencies {
    implementation(libs.jakarta.ws.rs.api)
    implementation(libs.jersey.container.servlet)
    implementation(libs.jersey.hk2)
    implementation(libs.jersey.server)
    implementation(libs.jetty.server)
    implementation(libs.jetty.servlet)
    implementation(libs.logback.classic)

    implementation(project(":api"))
    implementation(project(":data"))
    implementation(project(":net"))
}
