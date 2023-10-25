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
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    // klibs
    implementation(klibs.klibs.kdi)
    // Velocity
    compileOnly(libs.minecraft.velocity.api)
    annotationProcessor(libs.minecraft.velocity.api)
    // Local
    implementation(projects.modules.shared)
}

buildConfig {
    className("BuildKonfig")
    packageName(projectInfo.group)
    fun buildConfigStringField(name: String, value: String) {
        buildConfigField("String", name, "\"${value}\"")
    }
    buildConfigStringField("id", projectInfo.name.lowercase())
    buildConfigStringField("name", projectInfo.name)
    buildConfigStringField("version", projectInfo.versionString)
    buildConfigStringField("url", projectInfo.url)
    buildConfigStringField("description", projectInfo.description)
    projectInfo.developersList.forEachIndexed { i, dev ->
        buildConfigStringField("author_$i", dev.id)
    }
}

setupVelocityProcessor()
setupSpigotShadow {
    archiveBaseName.set("${projectInfo.name}-velocity")
}
