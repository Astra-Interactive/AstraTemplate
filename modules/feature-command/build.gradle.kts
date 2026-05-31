plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.gradleplugin.detekt")
    id("ru.astrainteractive.gradleplugin.java.version")
}

dependencies {
    compileOnly(libs.guava)
    compileOnly(libs.minecraft.brigadier)
    compileOnly(libs.minecraft.kyori.plain)

    implementation(libs.klibs.kstorage)
    implementation(libs.klibs.mikro.core)
    implementation(libs.klibs.mikro.extensions)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.minecraft.astralibs.command)
    implementation(libs.minecraft.astralibs.core)

    implementation(projects.modules.core)
    implementation(projects.modules.featureGui.api)
    implementation(projects.modules.api.remote)
    implementation(projects.modules.api.local)
    implementation(projects.modules.buildKonfig)

    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.tests.kotlin.test)
}
