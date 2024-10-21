import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    kotlin("jvm")
    alias(libs.plugins.gradle.shadow)
    alias(libs.plugins.gradle.buildconfig)
    alias(libs.plugins.klibs.minecraft.shadow)
    alias(libs.plugins.klibs.minecraft.resource.processor)
}

dependencies {
    implementation(libs.bundles.kotlin)
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.command)
    implementation(libs.klibs.kstorage)
    // Velocity
    compileOnly(libs.minecraft.velocity.api)
    annotationProcessor(libs.minecraft.velocity.api)
    // Local
    implementation(projects.modules.core)
    implementation(projects.modules.buildKonfig)
}

minecraftProcessResource {
    velocityResourceProcessor.process()
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
        archiveBaseName.set("${projectInfo.name}-velocity")
        destinationDirectory.set(destination.get())
    }
}
