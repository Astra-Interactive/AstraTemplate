plugins {
    java
    `java-library`
    `maven-publish`
    alias(libs.plugins.gradle.buildconfig) apply false
    alias(libs.plugins.gradle.forgegradle) apply false
    alias(libs.plugins.gradle.neoforgegradle) apply false
    alias(libs.plugins.klibs.gradle.detekt)
    alias(libs.plugins.klibs.gradle.dokka) apply false
    alias(libs.plugins.klibs.gradle.java.version) apply false
    alias(libs.plugins.klibs.gradle.publication) apply false
    alias(libs.plugins.klibs.gradle.rootinfo) apply false
    alias(libs.plugins.klibs.minecraft.resource.processor) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.serialization) apply false
}
