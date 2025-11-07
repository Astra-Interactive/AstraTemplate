plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.kaml)

    implementation(libs.minecraft.astralibs.core)

    implementation(libs.klibs.kstorage)
    implementation(libs.klibs.mikro.core)
    implementation(libs.klibs.mikro.extensions)

    implementation(libs.exposed.core)

    implementation(projects.modules.core)
}
