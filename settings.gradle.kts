pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "AstraTemplate"
include("domain")
include("plugin")
include("fabric")
