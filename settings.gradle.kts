pluginManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        gradlePluginPortal()
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://nexus.scarsz.me/content/groups/public/")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://repo.essentialsx.net/snapshots/")
        maven("https://files.minecraftforge.net/maven")
        maven("https://repo.maven.apache.org/maven2/")
        maven("https://maven.neoforged.net/releases")
        maven("https://maven.enginehub.org/repo/")
        maven("https://maven.minecraftforge.net")
        maven("https://repo1.maven.org/maven2/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.playpro.com")
        maven("https://jitpack.io")
    }
}

dependencyResolutionManagement {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
        maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
        maven("https://papermc.io/repo/repository/maven-public/")
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://oss.sonatype.org/content/groups/public/")
        maven("https://nexus.scarsz.me/content/groups/public/")
        maven("https://repo.dmulloy2.net/repository/public/")
        maven("https://repo.essentialsx.net/snapshots/")
        maven("https://repo.essentialsx.net/releases/")
        maven("https://files.minecraftforge.net/maven")
        maven("https://repo.maven.apache.org/maven2/")
        maven("https://maven.enginehub.org/repo/")
        maven("https://maven.minecraftforge.net/")
        maven("https://libraries.minecraft.net/")
        maven("https://repo1.maven.org/maven2/")
        maven("https://maven.fabricmc.net/")
        maven("https://maven.playpro.com")
        maven("https://jitpack.io")
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "AstraTemplate"

include(":instances:bukkit")
include(":instances:forge")
include(":instances:neoforge")

include("modules:api:remote")
include("modules:api:local")
include("modules:core")
include("modules:build-konfig")
include("modules:feature-command")
include("modules:feature-gui:api")
include("modules:feature-gui:bukkit")
include("modules:feature-event:bukkit")
include("modules:feature-event:forge")
include("modules:feature-event:neoforge")
