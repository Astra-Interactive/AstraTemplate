import ru.astrainteractive.gradleplugin.setupSpigotProcessor
import ru.astrainteractive.gradleplugin.setupSpigotShadow
import ru.astrainteractive.gradleplugin.util.ProjectProperties.projectInfo

plugins {
    kotlin("jvm")
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.minecraft.astralibs.spigot.gui)
    implementation(libs.minecraft.astralibs.spigot.core)
    implementation(klibs.klibs.mikro.core)
    // Spigot dependencies
    compileOnly(libs.minecraft.paper.api)
    implementation(libs.minecraft.bstats)
    // Local
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.dto)
    implementation(projects.modules.shared)
}

setupSpigotProcessor()

val destination = File("D:\\Minecraft Servers\\Servers\\esmp-configuration\\anarchy\\plugins")
    .takeIf(File::exists)
    ?: File(rootDir, "jars")

setupSpigotShadow(destination) {
    archiveBaseName.set("${projectInfo.name}-bukkit")
}
