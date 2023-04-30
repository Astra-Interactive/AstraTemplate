plugins {
    id("spigot-resource-processor")
    id("spigot-shadow")
    id("basic-java")
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.minecraft.astralibs.di)
    implementation(libs.minecraft.astralibs.spigot.gui)
    implementation(libs.minecraft.astralibs.spigot.core)
    // Spigot dependencies
    compileOnly(libs.minecraft.paper.api)
    implementation(libs.minecraft.bstats)
    // Test
    testImplementation(platform(libs.tests.junit.bom))
    testImplementation(libs.bundles.testing.libs)
    testImplementation(libs.bundles.testing.kotlin)
    testImplementation(libs.minecraft.mockbukkit)
    // Local
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.dto)
}
