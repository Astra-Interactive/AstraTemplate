[![kotlin_version](https://img.shields.io/badge/kotlin-1.8.10-blueviolet?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![kotlin_version](https://img.shields.io/badge/java-19-blueviolet?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![minecraft_version](https://img.shields.io/badge/minecraft-1.19.4-green?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)
[![platforms](https://img.shields.io/badge/platform-spigot%7Cfabric%7Cvelocity-blue?style=flat-square)](https://github.com/Astra-Interactive/AstraLibs)

# AstraTemplate

### MultiPlatform (Plugin-first) Spigot/Velocity plugin

This is a Minecraft Multiplatform template that provides architecture and various tools you'll need to create new
Spigot/Velocity plugins as fast as possible

<h4 align="center">☄️ Plugins based on AstraTemplate☄️ </h4>

<p align="center">
    <a href="https://github.com/Astra-Interactive/AstraAuctions/">
        <img alt="spigot" src="https://img.shields.io/badge/github-AstraAuctions-1B76CA"/>
    </a>
    <a href="https://github.com/Astra-Interactive/AstraRating">
        <img alt="spigot" src="https://img.shields.io/badge/github-AstraRating -1B76CA"/>
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

## Novice developer?

This project can be very difficult for novice developers. Especially for those who were working with java.

## Overview

AstraTemplate and it's libs design after more than 2 years of developing spigot plugins and android applications.
It contains powerful and scalable architecture template which will help you in your development.

## 1. Directory structure

    ├── build-logic         # Build components
    ├── modules             
    │   ├── api-local       # Local api with SQLite
    │   ├── api-remote      # Remote sample RickMorty API
    │   └── dto             # DTO objects for shared usage
    ├── forge               # Forge template mod
    ├── fabric              # Fabric template mod
    ├── velocity            # Velocity template plugin
    └── plugin              # Spigot template mod

## 2. Build convention

#### 2.1 `basic-java.gradle.kts`

This convention plugin contains default java configuration

#### 2.2 `xxx-resource-processor.gradle.kts`

This convention plugin contains basic resource processor.
For example **spigot-resource-processor.gradle.kts** will replace **name**, **prefix** etc in your plugin.yml

#### 2.3 `xxx-shadow.gradle.kts`

This convention plugin will create default shadowJar task for module

## 3. Modules

#### 3.1 `api-local`

This module contains local API with sqlite database. It contains no spigot/velocity/fabric dependencies.
Jvm only. Due to this factor, you can easily share this module between your spigot/velocity plugin or fabric/forge.

With this module you will be only dependent on LocalApi, which is an interface.

Currently AstraLibs-orm is used for SQLite api-local, but you can replace it with anything you want.
Only implementation will be changed, but LocalApi will be untouched, also as other functionality of your plugin/mod

#### 3.2 `api-remote`

This module contains remote api with RickMortyApi. It will return random character with suspend async response.
Like `api-local`, this module also contains only jvm dependencies, so can be used in spigot/fabrict and others.

#### 3.3 `dto`

Sometimes you need to share models between other modules, so this module contains shared data models.

## 4. Velocity [wip]

I've not been working with velocity too much, but this module contains basic velocity functionality with plugin
information generation.

## 5. Plugin

This plugin contains advanced and powerful spigot functionality

- GUI
- Commands
- Events
- Translation
- DI
- Permissions
- Configuration

### Platforms

- [x] Spigot/Paper
- [ ] Fabric - Pre Alpha state
- [ ] Forge - Pre-Alpha state
- [x] Velocity/Bungee

### Build jar executables

Firstly, change gradle/libs.versions.toml destination-xxx to your folder

    $ ./gradlew :plugin:shadowJar          # assemble the plugin .jar
    $ ./gradlew :velocity:shadowJar        # assemble the plugin .jar

### Forge and fabirc is on pause

Also, checkout [AstraLearner](https://play.google.com/store/apps/details?id=com.makeevrserg.astralearner) - it will help
you to learn foreign words easily!

### Afterword

AstraTemplate highly dependent on self-written open source libraries

- [AstraLibs](https://github.com/Astra-Interactive/AstraLibs) - Minecraft development
- [klibs.mikro](https://github.com/makeevrserg/klibs.mikro) - Mapper, UseCase, Dispatchers interface
- [klibs.kstorage](https://github.com/makeevrserg/klibs.kstorage) - Key-value storage wrapper
- [klibs.kdi](https://github.com/makeevrserg/klibs.kdi) - Manual DI