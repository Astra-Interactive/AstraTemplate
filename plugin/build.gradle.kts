plugins {
    id("spigot-resource-processor")
    id("spigot-shadow")
    id("basic-java")
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
    implementation(libs.astralibs.spigotGui)
    implementation(libs.astralibs.spigotCore)
    implementation(libs.bstats.bukkit)
    // Spigot dependencies
    compileOnly(libs.paperApi)
//    compileOnly(libs.spigotApi)
//    compileOnly(libs.spigot)
    // Test-Core
    testImplementation(platform(libs.junit.bom))
    testImplementation(kotlin("test-junit5"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    // Test-libs
    testImplementation(libs.coroutines.core)
    testImplementation(libs.coroutines.coreJvm)
    testImplementation(libs.xerial.sqlite.jdbc)
    // Local
    implementation(project(":modules:api-local"))
    implementation(project(":modules:api-remote"))
    implementation(project(":modules:dto"))
}
