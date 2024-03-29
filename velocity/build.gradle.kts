import ru.astrainteractive.gradleplugin.setupSpigotShadow
import ru.astrainteractive.gradleplugin.setupVelocityProcessor
import ru.astrainteractive.gradleplugin.util.ProjectProperties.projectInfo

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
    implementation(klibs.klibs.kdi)
    implementation(libs.minecraft.astralibs.command)
    // Velocity
    compileOnly(libs.minecraft.velocity.api)
    annotationProcessor(libs.minecraft.velocity.api)
    // Local
    implementation(projects.modules.core)
    implementation(projects.modules.buildKonfig)
}

setupVelocityProcessor()
setupSpigotShadow(File("D:\\Minecraft Servers\\Servers\\esmp-configuration\\velocity\\plugins")) {
    archiveBaseName.set("${projectInfo.name}-velocity")
}
