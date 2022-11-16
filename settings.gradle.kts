
pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://files.minecraftforge.net/maven")
        maven("https://dist.creeper.host/Sponge/maven")
        maven("https://plugins.gradle.org/m2/")
        mavenCentral()
        gradlePluginPortal()
    }
}
buildscript {

    repositories {
        maven("https://files.minecraftforge.net/maven")
        maven("https://dist.creeper.host/Sponge/maven")
        maven("https://plugins.gradle.org/m2/")
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:5.1.+")
    }
}
rootProject.name = "AstraTemplate"
include("domain")
include("plugin")
include("fabric")
include("forge")
