[![kotlin_version](https://img.shields.io/badge/kotlin-1.8.10-blueviolet?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![kotlin_version](https://img.shields.io/badge/java-19-blueviolet?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![minecraft_version](https://img.shields.io/badge/minecraft-1.19.4-green?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![platforms](https://img.shields.io/badge/platform-spigot%7Cfabric%7Cvelocity-blue?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)

# AstraTemplate

### MultiPlatform (Plugin-first) Spigot/Velocity/Fabric plugin

This is a Minecraft Multiplatform template that provides architecture and all(probably) tools you'll need to create new
Spigot/Velocity plugins as fast as possible

AstraTemplate highly depends on our
library [AstraLibs](https://github.com/Asrta-Interactive/AstraTemplate/wiki/AstraLibs)

<h4 align="center">☄️ Plugins based on AstraTemplate☄️ </h4>

<p align="center">
    <a href="https://github.com/Astra-Interactive/AstraLibs">
        <img alt="spigot" src="https://img.shields.io/badge/github-AstraLibs-1B76CA"/>
    </a>    
    <a href="https://www.spigotmc.org/resources/astra-market.99114/">
        <img alt="spigot" src="https://img.shields.io/badge/github-AstraMarket-1B76CA"/>
    </a>
    <a href="https://www.spigotmc.org/resources/simple-rating.103317/">
        <img alt="spigot" src="https://img.shields.io/badge/github-SimpleRating-1B76CA"/>
    </a>
    <a href="https://github.com/Astra-Interactive/AspeKt">
        <img alt="spigot" src="https://img.shields.io/badge/github-AspeKt-1B76CA"/>
    </a>
    <a href="https://github.com/Astra-Interactive/AstraShop">
        <img alt="spigot" src="https://img.shields.io/badge/github-AstraShop-1B76CA"/>
    </a>
    <a href="https://github.com/Astra-Interactive/KapitalystiK">
        <img alt="spigot" src="https://img.shields.io/badge/github-KapytalystiK[WIP]-1B76CA"/>
    </a>
    <a href="https://github.com/Astra-Interactive/SynK">
        <img alt="spigot" src="https://img.shields.io/badge/github-SynK[WIP]-1B76CA"/>
    </a>
</p>

## Brief description for AstraTemplate

Astra template has everything (probably) you need including:

- [x] Paginated menu example
- [x] Menu live-update example
- [x] EventManager and Event example
- [x] Commands example
- [x] TabCompleter example
- [x] Database example
- [x] Permissions example
- [x] FileManager example
- [x] YML Config loader example
- [x] Translation config example
- [x] Plugin reload
- [x] PlugMan support
- [x] GSON-like YML loader using GSON or kotlinx.serialization
- [x] Logger, which can be logged in file
- [x] Readable code
- [x] (almost) Everything is commented
- [x] Error catching
- [x] Enum extensions
- [x] DTO And domain structure
- [x] Multimodules structure
- [x] DSL Database
- [x] Easy DI aka Dependency Injection
- [x] MVI GUI example
- [x] And more other stuff - better look by yourself

# Platforms

- [x] Spigot/Paper
- [x] Fabric - Pre Alpha state
- [ ] Forge - Pre-Alpha state
- [x] Velocity/Bungee

## Directory structure

    ├── build-logic         # Build components
    ├── modules             
    │   ├── api-local       # Local api with SQLite
    │   ├── api-remote      # Remote sample RickMorty API
    │   └── dto             # DTO objects for shared usage
    ├── forge               # Forge template mod
    ├── fabric              # Fabric template mod
    ├── velocity            # Velocity template plugin
    └── plugin              # Spigot template mod

## Build jar executables

Firstly, change gradle/libs.versions.toml destination-xxx to your folder

    $ ./gradlew :plugin:shadowJar          # assemble the plugin .jar
    $ ./gradlew :fabric:build              # assemble the Fabric .jar
    $ ./gradlew :velocity:shadowJar        # assemble the plugin .jar

## Forge status

There's gradle 8.0 and ```net.minecraftforge.gradle``` can't be applied at moment of 19.03.2023

Also, checkout [AstraLearner](https://play.google.com/store/apps/details?id=com.makeevrserg.astralearner) - it will help
you to learn foreign words easily!