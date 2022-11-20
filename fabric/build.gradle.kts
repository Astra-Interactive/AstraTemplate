import net.fabricmc.loom.task.RemapJarTask
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.ir.backend.js.compile

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
    implementation("org.xerial:sqlite-jdbc:3.34.0")
    implementation(project(":domain"))
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
        include(dependency(Dependencies.Libraries.astraLibsKtxCore))
        include(dependency(":domain"))
        include(dependency("org.xerial:sqlite-jdbc:3.34.0"))
    }
    exclude("mappings/")
    dependsOn(configurations)
    from(sourceSets.main.get().allSource)
    mergeServiceFiles()
//    minimize()
    isReproducibleFileOrder = true
    archiveClassifier.set(null as String?)
    archiveBaseName.set("AstraTemplate")
}

val remapJar = tasks.getByName<RemapJarTask>("remapJar") {
    dependsOn(shadowJar)
    mustRunAfter(shadowJar)
    this.input.set(shadowJar.archiveFile)
    addNestedDependencies.set(true)
    archiveBaseName.set("AstraTemplate")
    destinationDirectory.set(File(Dependencies.destinationDirectoryFabricPath))
}
tasks.assemble {
    dependsOn(remapJar)
}

artifacts {
    archives(remapJar)
    shadow(shadowJar)
}