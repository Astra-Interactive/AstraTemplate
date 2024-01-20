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
    implementation(klibs.klibs.kdi)
    implementation(klibs.klibs.mikro.core)
}
