plugins {
    kotlin("jvm")
}
dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    // klibs
//    implementation(libs.bundles.klibs)
    // Local
    implementation(projects.modules.dto)
}
