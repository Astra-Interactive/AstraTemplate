import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    kotlin("jvm")
    alias(libs.plugins.klibs.gradle.java.core)
    alias(libs.plugins.klibs.minecraft.shadow)
    alias(libs.plugins.klibs.minecraft.resource.processor)
}

dependencies {
    // Kotlin
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.command)
    implementation(libs.minecraft.astralibs.command.bukkit)
    implementation(libs.minecraft.astralibs.menu.bukkit)
    implementation(libs.minecraft.astralibs.core.bukkit)
    implementation(libs.klibs.mikro.core)
    implementation(libs.klibs.kstorage)
    // Spigot dependencies
    compileOnly(libs.minecraft.paper.api)
    implementation(libs.minecraft.bstats)
    // Local
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.core)
}

minecraftProcessResource {
    spigotResourceProcessor.process()
}

setupShadow {
    requireShadowJarTask {
        destination = File("/home/makeevrserg/Desktop/server/data/plugins")
            .takeIf { it.exists() }
            ?: File(rootDir, "jars")

        val projectInfo = requireProjectInfo
        isReproducibleFileOrder = true
        mergeServiceFiles()
        dependsOn(configurations)
        archiveClassifier.set(null as String?)
        relocate("org.bstats", projectInfo.group)

        minimize {
            exclude(dependency(libs.exposed.jdbc.get()))
            exclude(dependency(libs.exposed.dao.get()))
            exclude(dependency("org.jetbrains.kotlin:kotlin-stdlib:${libs.versions.kotlin.version.get()}"))
        }
        archiveVersion.set(projectInfo.versionString)
        archiveBaseName.set("${projectInfo.name}-bukkit")
        destinationDirectory.set(destination.get())
    }
}
