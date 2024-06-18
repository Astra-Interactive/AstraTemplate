import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo
import ru.astrainteractive.gradleplugin.setupSpigotShadow
import ru.astrainteractive.gradleplugin.setupVelocityProcessor

plugins {
    kotlin("jvm")
    alias(libs.plugins.gradle.shadow)
    alias(libs.plugins.gradle.buildconfig)
}

dependencies {
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.orm)
    // klibs
    implementation(libs.klibs.kdi)
    implementation(libs.minecraft.astralibs.command)
    // Velocity
    compileOnly(libs.minecraft.velocity.api)
    annotationProcessor(libs.minecraft.velocity.api)
    // Local
    implementation(projects.modules.core)
    implementation(projects.modules.buildKonfig)
}

setupVelocityProcessor()

val destination = File("C:\\Users\\Roman\\Desktop\\ForgeTest\\mods")
    .takeIf(File::exists)
    ?: File(rootDir, "jars")

setupSpigotShadow(destination) {
    archiveBaseName.set("${requireProjectInfo.name}-velocity")
}
