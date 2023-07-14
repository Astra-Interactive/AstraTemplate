plugins {
    id("basic-java")
}
dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.klibs.kdi)
    // Local
    implementation(projects.modules.dto)
}
