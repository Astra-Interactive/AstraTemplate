plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    // klibs
    implementation(klibs.klibs.kdi)
    // Test
    testImplementation(libs.bundles.testing.kotlin)
    testImplementation(libs.tests.kotlin.test)
}
