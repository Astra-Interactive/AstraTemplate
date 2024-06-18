plugins {
    kotlin("jvm")
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.minecraft.astralibs.core)
    // klibs
    implementation(libs.klibs.kdi)
    implementation(libs.klibs.mikro.core)
}
