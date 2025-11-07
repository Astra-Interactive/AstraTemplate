plugins {
    kotlin("jvm")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.kaml)

    implementation(libs.minecraft.astralibs.core)

    implementation(libs.klibs.mikro.core)
    implementation(libs.klibs.mikro.extensions)
    api(libs.klibs.kstorage)
}
