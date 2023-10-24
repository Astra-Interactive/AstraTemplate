plugins {
    kotlin("jvm")
}
dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    // klibs
//    implementation(libs.bundles.klibs)
    // Local
    implementation(projects.modules.dto)
}
