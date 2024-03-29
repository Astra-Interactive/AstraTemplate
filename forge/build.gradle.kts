@file:Suppress("UnusedPrivateMember")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import ru.astrainteractive.gradleplugin.models.Developer
import ru.astrainteractive.gradleplugin.util.ProjectProperties.projectInfo

plugins {
    kotlin("jvm")
    alias(klibs.plugins.klibs.gradle.java.core)
    id("net.minecraftforge.gradle") version ("[6.0,6.2)")
    id("com.github.johnrengelman.shadow")
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
                create(projectInfo.name) {
                    source(sourceSets.getByName("main"))
                }
            }
        }
    }
}

dependencies {
    minecraft("net.minecraftforge:forge:1.20.1-47.1.46")
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.command)
    implementation(klibs.klibs.kdi)
    // Kotlin
    implementation(libs.bundles.kotlin)
    // Local
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.core)
    implementation(projects.modules.buildKonfig)
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

val processResources = project.tasks.withType<org.gradle.language.jvm.tasks.ProcessResources>() {
    filteringCharset = "UTF-8"
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    from(sourceSets.main.get().resources.srcDirs) {
        include("META-INF/mods.toml")
        include("mods.toml")
        expand(
            "modId" to projectInfo.name.lowercase(),
            "version" to projectInfo.versionString,
            "description" to projectInfo.description,
            "displayName" to projectInfo.name,
            "authors" to projectInfo.developersList.map(Developer::id).joinToString(",")
        )
    }
}

val destination = File("C:\\Users\\Roman\\Desktop\\EsmpModded\\server\\mods")
    .takeIf(File::exists)
    ?: File(rootDir, "jars")

val reobfShadowJar = reobf.create("shadowJar")

val shadowJar by tasks.getting(ShadowJar::class) {
    mustRunAfter(processResources)
    dependsOn(processResources)
    dependencies {
        // Kotlin
        include(dependency("ru.astrainteractive.klibs:kdi-jvm"))
        include(dependency(libs.minecraft.astralibs.core.asProvider().get()))
        include(dependency(libs.minecraft.astralibs.command.asProvider().get()))
        include(dependency(projects.modules.core))
        include(dependency(projects.modules.apiRemote))
        include(dependency(projects.modules.apiLocal))
        // TODO somehow fix the multiplatform libraries
        include(dependency("ru.astrainteractive.klibs:kdi-jvm"))
        include(dependency("ru.astrainteractive.klibs:kdi-jvm"))
        include(dependency("org.jetbrains.kotlin:kotlin-stdlib"))
        include(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm"))
        include(dependency(libs.driver.jdbc.get()))
    }
    relocate("kotlin", "${projectInfo.group}.kotlin")
    relocate("kotlinx", "${projectInfo.group}.kotlinx")
    relocate("org.jetbrains", "${projectInfo.group}.org.jetbrains")
    mergeServiceFiles()
    manifest {
        attributes(
            "Specification-Title" to project.name,
            "Specification-Vendor" to projectInfo.developersList.first().id,
            "Specification-Version" to project.version,
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to projectInfo.developersList.first().id
        )
    }
    isReproducibleFileOrder = true
    archiveClassifier.set(null as String?)
    archiveBaseName.set("${projectInfo.name}-forge-shadow")
    destinationDirectory.set(destination)
    finalizedBy(reobfShadowJar)
}
