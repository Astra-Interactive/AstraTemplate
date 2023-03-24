group = libs.versions.group
version = libs.versions.plugin

plugins {
    java
    `maven-publish`
    `java-library`
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.shadow) apply false
    alias(libs.plugins.fabricLoom) apply false
    alias(libs.plugins.netMinecraftForge) apply false
    id("detekt-convention")
}
