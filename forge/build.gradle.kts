import org.jetbrains.kotlin.gradle.plugin.mpp.pm20.archivesName

group = Dependencies.group
version = Dependencies.version
java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

plugins {
    kotlin("jvm")
    id("net.minecraftforge.gradle")
    `maven-publish`
    java
    id("com.github.johnrengelman.shadow")
}

repositories {
    maven("https://files.minecraftforge.net/maven")
    maven("https://dist.creeper.host/Sponge/maven")
    maven("https://plugins.gradle.org/m2/")
}

minecraft {

    mappings("official", "1.19")
    runs {
        // Run ./gradlew genIntellijRuns
        println(this.names)
//        val client by getting {
//            workingDirectory(project.file("run"))
//            property("forge.logging.markers", "REGISTRIES")
//            property("forge.logging.console.level", "debug")
//            mods {
//                this.getByName("AstraTemplate") {
//                    source(sourceSets.main.get())
//                }
//            }
//        }
//        val server by getting {
//            workingDirectory(project.file("run"))
//            property("forge.logging.markers", "REGISTRIES")
//            property("forge.logging.console.level", "debug")
//            mods {
//                this.getByName("AstraTemplate") {
//                    source(sourceSets.main.get())
//                }
//            }
//        }
    }
}
java {
    withSourcesJar()
}
dependencies {
    minecraft("net.minecraftforge:forge:${Dependencies.Forge.forge}")
}
tasks {
    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            filesMatching("mods.toml") {
                expand(
                    "{displayName}" to "AstraTemplate",
                    "{version}" to project.version,
                    "{description}" to project.description
                )
            }
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
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
    destinationDirectory.set(File(Dependencies.destinationDirectoryForgePath))
}
