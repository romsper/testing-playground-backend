val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val hikari_version: String by project
val flyway_version: String by project
val exposed_version: String by project
val joda_time: String by project

plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.serialization") version "2.1.20"
    id("io.ktor.plugin") version "3.1.2"
    id("com.avast.gradle.docker-compose") version "0.17.12"
}

group = "com"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

tasks.register<Delete>("cleanLog") {
    delete(file("logs/application.log"))
}

tasks.named("run") {
    dependsOn("cleanLog")
}

dependencies {

    // Ktor
    implementation("io.ktor:ktor-server-core:$ktor_version")
    implementation("io.ktor:ktor-server-auth:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-http-redirect:$ktor_version")
    implementation("io.ktor:ktor-server-netty:$ktor_version")
    implementation("io.ktor:ktor-server-host-common:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

    // Serialization - not sure that they are required
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")

    // Logs
    implementation("ch.qos.logback:logback-classic:$logback_version")

    // Database
    implementation("org.postgresql:postgresql:$postgres_version")
    implementation("com.zaxxer:HikariCP:$hikari_version")
    implementation("org.flywaydb:flyway-core:$flyway_version")
    implementation("org.flywaydb:flyway-database-postgresql:$flyway_version")
    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-json:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")

    // Utils
    implementation("joda-time:joda-time:$joda_time")

    // Tests
    testImplementation("io.ktor:ktor-server-tests-jvm:3.0.0-beta-1")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
}

dockerCompose {
    useComposeFiles.set(listOf("${rootProject.projectDir}/docker-compose.yaml"))
    isRequiredBy(tasks.named("run"))
}