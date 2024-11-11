@file:Suppress("UnusedPrivateMember")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import ru.astrainteractive.gradleplugin.model.Developer
import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    kotlin("jvm")
    alias(libs.plugins.klibs.gradle.java.core)
    id("net.minecraftforge.gradle") version ("[6.0,6.2)")
    id("com.github.johnrengelman.shadow")
}

val shade by configurations.creating

val implementation by configurations.getting {
    this.extendsFrom(shade)
}

dependencies {
    minecraft("net.minecraftforge:forge:1.21.3-53.0.11")
    // AstraLibs
    shade(libs.minecraft.astralibs.core)
    shade(libs.minecraft.astralibs.command)
    shade(libs.klibs.kstorage)
    // Kotlin
    shade(libs.bundles.kotlin)
    // Local
    shade(projects.modules.apiLocal)
    shade(projects.modules.apiRemote)
    shade(projects.modules.core)
    shade(projects.modules.buildKonfig)
}

minecraft {
    mappings("official", "1.20.1")
    runs {
        val runProperties = mapOf(
            // Recommended logging data for a userdev environment.
            Pair("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP"),
            // Recommended logging level for the console.
            Pair("forge.logging.console.level", "debug")
        )
        val server by creating {
            properties(runProperties)
            workingDirectory(File("./build${File.separator}run"))
            mods {
                create(requireProjectInfo.name) {
                    source(sourceSets.getByName("main"))
                }
            }
        }
    }
}

configurations {
    apiElements {
        artifacts.clear()
    }
    runtimeElements {
        setExtendsFrom(emptySet())
        // Publish the jarJar
        artifacts.clear()
        outgoing.artifact(tasks.jarJar)
    }
}

val processResources = project.tasks.withType<ProcessResources> {
    filteringCharset = "UTF-8"
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(sourceSets.main.get().resources.srcDirs) {
        include("META-INF/mods.toml")
        include("mods.toml")
        expand(
            "modId" to requireProjectInfo.name.lowercase(),
            "version" to requireProjectInfo.versionString,
            "description" to requireProjectInfo.description,
            "displayName" to requireProjectInfo.name,
            "authors" to requireProjectInfo.developersList.map(Developer::id).joinToString(",")
        )
    }
}

val destination = File("D:\\Minecraft Servers\\server-docker-forge\\data\\mods")
    .takeIf(File::exists)
    ?: File(rootDir, "jars")

val reobfShadowJar = reobf.create("shadowJar")

val shadowJar by tasks.getting(ShadowJar::class) {
    mustRunAfter(processResources)
    dependsOn(processResources)
    configurations = listOf(shade)
    dependencies {
        exclude(dependency("org.jetbrains:annotations"))
        exclude("DebugProbesKt.bin")
        exclude("_COROUTINE/**")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/com.android.tools/**")
        exclude("META-INF/gradle-plugins/**")
        exclude("META-INF/maven/**")
        exclude("META-INF/proguard/**")
        exclude("META-INF/services/**")
        exclude("META-INF/versions/**")
    }
    // Be sure to relocate EXACT PACKAGES!!
    // For example, relocate org.some.package instead of org
    // Becuase relocation org will break other non-relocated dependencies such as org.minecraft
    listOf(
        "okio",
        "org.slf4j",
        "kotlin",
        "kotlinx",
        "it.krzeminski",
        "net.thauvin",
        "org.jetbrains.exposed",
        "org.jetbrains.kotlin",
        "org.jetbrains.kotlinx",
        "com.charleskorn",
        "ru.astrainteractive.klibs",
        "ru.astrainteractive.astralibs",
    ).forEach { pattern -> relocate(pattern, "${requireProjectInfo.group}.shade.$pattern") }
    mergeServiceFiles()
    manifest {
        attributes(
            "Specification-Title" to project.name,
            "Specification-Vendor" to requireProjectInfo.developersList.first().id,
            "Specification-Version" to project.version,
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to requireProjectInfo.developersList.first().id
        )
    }
    isReproducibleFileOrder = true
    archiveClassifier.set(null as String?)
    archiveBaseName.set("${requireProjectInfo.name}-forge")
    destinationDirectory.set(destination)
    finalizedBy(reobfShadowJar)
}
