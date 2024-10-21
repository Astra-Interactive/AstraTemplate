plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
}
