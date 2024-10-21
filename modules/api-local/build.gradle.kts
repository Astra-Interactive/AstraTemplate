plugins {
    kotlin("jvm")
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.exposed)
    // mikro
    implementation(libs.klibs.kstorage)
    // Exposed
    implementation(libs.bundles.exposed)
    // klibs
    implementation(libs.klibs.mikro.core)
    // Local
    implementation(projects.modules.core)
}
