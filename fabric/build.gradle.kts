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
    // AstraLibs
    implementation(Dependencies.Libraries.astraLibsKtxCore)
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
val remapJar = tasks.getByName<RemapJarTask>("remapJar") {
//    dependsOn(shadowJar)
//    mustRunAfter(shadowJar)
//    this.nestedJars.setBuiltBy(listOf(shadowJar))
//    this.from(shadowJar.source)
    archiveBaseName.set("AstraTemplate")
    destinationDirectory.set(File(Dependencies.destinationDirectoryFabricPath))
}
val shadowJar by tasks.getting(ShadowJar::class) {
    dependsOn(remapJar)
    mustRunAfter(remapJar)

    dependencies {
        // Kotlin
        include(dependency(Dependencies.Libraries.kotlinGradlePlugin))
        include(dependency(Dependencies.Libraries.astraLibsKtxCore))
    }
    exclude("mappings/")
    isReproducibleFileOrder = true
    mergeServiceFiles()
//    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    archiveBaseName.set("AstraTemplate")
    destinationDirectory.set(File(Dependencies.destinationDirectoryFabricPath))
}
