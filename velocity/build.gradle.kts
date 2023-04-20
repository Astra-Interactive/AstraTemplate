import buildlogic.ProjectConfig.info

plugins {
    id("spigot-shadow")
    id("basic-java")
    id("com.github.johnrengelman.shadow")
    id("velocity-resource-processor")
    id("velocity-shadow")
    alias(libs.plugins.buildconfig)
}

dependencies {
    // Kotlin
    implementation(libs.kotlinGradlePlugin)
    // Coroutines
    implementation(libs.coroutines.coreJvm)
    implementation(libs.coroutines.core)
    // Serialization
    implementation(libs.kotlin.serialization)
    implementation(libs.kotlin.serializationJson)
    implementation(libs.kotlin.serializationKaml)
    // AstraLibs
    implementation(libs.astralibs.ktxCore)
    implementation(libs.astralibs.orm)
    // Velocity
    compileOnly(libs.velocity.api)
    annotationProcessor(libs.velocity.api)
    // Test-Core
    testImplementation(platform(libs.junit.bom))
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Test-libs
    testImplementation(libs.coroutines.core)
    testImplementation(libs.coroutines.coreJvm)
    testImplementation(libs.xerial.sqlite.jdbc)
}

buildConfig {
    className("BuildKonfig")
    packageName(libs.versions.group.get())
    fun buildConfigStringField(name: String, value: String) {
        buildConfigField("String", name, "\"${value}\"")
    }
    buildConfigStringField("id", info.id)
    buildConfigStringField("name", info.name)
    buildConfigStringField("version", info.version)
    buildConfigStringField("url", info.url)
    buildConfigStringField("description", info.description)
//    buildConfigField("List<String>", "authors", info.authors.joinToString("\",\"","listOf(\"","\")"))
    buildConfigStringField("author", info.authors.first())
}
