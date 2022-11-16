import net.fabricmc.loom.task.RemapJarTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
    kotlin("jvm")
    id("fabric-loom")
    `maven-publish`
    java
    id("com.github.johnrengelman.shadow")
}

group = Dependencies.group
version = Dependencies.version

dependencies {
    mappings("net.fabricmc:yarn:${Dependencies.Fabric.yarn}")
    minecraft("com.mojang:minecraft:${Dependencies.Fabric.minecraftVersion}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${Dependencies.Fabric.kotlin}")
    modImplementation("net.fabricmc:fabric-loader:${Dependencies.Fabric.fabricLoader}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${Dependencies.Fabric.fabricApi}")
}

tasks {

    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }

    jar {
        from("LICENSE")
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

}

java {
    withSourcesJar()
}



val shadowJar by tasks.getting(ShadowJar::class) {
    dependencies {
        // Kotlin
        include(dependency(Dependencies.Libraries.kotlinGradlePlugin))
    }
    exclude("mappings")
    exclude("mappings/")
    exclude("./mappings")
    isReproducibleFileOrder = true
    mergeServiceFiles()
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    archiveBaseName.set("AstraTemplate")
    destinationDirectory.set(File(Dependencies.destinationDirectoryFabricPath))
}

val remapJar = tasks.getByName<RemapJarTask>("remapJar") {
    (this as Task)
    dependsOn(shadowJar)
    mustRunAfter(shadowJar)
    this.nestedJars.setFrom(shadowJar.archivePath)
    this.nestedJars.setBuiltBy(listOf(shadowJar))
    archiveBaseName.set("AstraTemplate")
    destinationDirectory.set(File(Dependencies.destinationDirectoryFabricPath))
}