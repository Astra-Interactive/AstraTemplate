plugins {
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
    // Test
    testImplementation(kotlin("test"))
    testImplementation(libs.orgTesting)
    // Local
    implementation(project(":modules:dto"))
}
