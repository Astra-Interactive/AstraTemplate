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
    minecraft("com.mojang:minecraft:${Dependencies.Fabric.minecraftVersion}")
    mappings("net.fabricmc:yarn:${Dependencies.Fabric.yarn}")
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



tasks.shadowJar {
    dependencies {
        // Kotlin
        include(dependency(Dependencies.Libraries.kotlinGradlePlugin))
    }
    isReproducibleFileOrder = true
    mergeServiceFiles()
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    from(sourceSets.main.get().output)
    minimize()
    archiveBaseName.set("AstraTemplate")
    destinationDirectory.set(File(Dependencies.destinationDirectoryFabricPath))
}
