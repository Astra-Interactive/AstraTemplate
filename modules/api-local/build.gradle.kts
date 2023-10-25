plugins {
    kotlin("jvm")
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.orm)
    // klibs
    implementation(klibs.klibs.kdi)
    implementation(klibs.klibs.mikro.core)
    // Local
    implementation(projects.modules.dto)
}
