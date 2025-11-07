@file:Suppress("UnusedPrivateMember")

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    kotlin("jvm")
    alias(libs.plugins.klibs.gradle.java.core)
    alias(libs.plugins.forgegradle)
    alias(libs.plugins.gradle.shadow)
    alias(libs.plugins.klibs.minecraft.resource.processor)
}

dependencies {
    minecraft(
        "net.minecraftforge",
        "forge",
        "${libs.versions.minecraft.mojang.version.get()}-${libs.versions.minecraft.forgeversion.get()}"
    )
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.core.forge)
    implementation(libs.minecraft.astralibs.command)

    implementation(libs.klibs.kstorage)
    implementation(libs.klibs.mikro.core)

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.kaml)

    implementation(projects.modules.apiLocal)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.core)
    implementation(projects.modules.buildKonfig)
}

minecraft {
    mappings("official", libs.versions.minecraft.mojang.version.get())
    accessTransformer(rootProject.file("build").resolve("accesstransformer.cfg"))
}

configurations {
    apiElements {
        artifacts.clear()
    }
    runtimeElements {
        setExtendsFrom(emptySet())
        // Publish the jarJar
        artifacts.clear()
        outgoing.artifact(tasks.jarJar)
    }
}

val destination = File("/home/makeevrsergh/Desktop/server/mods/")
    .takeIf(File::exists)
    ?: File(rootDir, "jars")

val reobfShadowJar = reobf.create("shadowJar")

val shadowJar by tasks.getting(ShadowJar::class) {
    mergeServiceFiles()
    finalizedBy(reobfShadowJar)
    configurations = listOf(project.configurations.shadow.get())
    isReproducibleFileOrder = true
    archiveClassifier = null as String?
    archiveVersion = requireProjectInfo.versionString
    archiveBaseName = "${requireProjectInfo.name}-forge"
    destinationDirectory = destination
    dependencies {
        exclude(dependency("org.jetbrains:annotations"))
        exclude("co/touchlab/stately/**")
        exclude("club/minnced/opus/**")
        exclude("com/google/**")
        exclude("com/sun/**")
        exclude("google/protobuf/**")
        exclude("io/github/**")
        exclude("io/javalin/**")
        exclude("jakarta/servlet/**")
        exclude("javax/annotation/**")
        exclude("javax/servlet/**")
        exclude("natives/**")
        exclude("nl/altindag/**")
        exclude("org/eclipse/**")
        exclude("org/bouncycastle/**")
        exclude("org/checkerframework/**")
        exclude("org/conscrypt/**")
        exclude("tomp2p/opuswrapper/**")
        exclude("DebugProbesKt.bin")
        exclude("_COROUTINE/**")
        exclude("META-INF/*.kotlin_module")
        exclude("META-INF/com.android.tools/**")
        exclude("META-INF/gradle-plugins/**")
        exclude("META-INF/maven/**")
        exclude("META-INF/proguard/**")
        exclude("META-INF/versions/**")
        exclude("META-INF/native/**")
        exclude("META-INF/**LICENCE**")
    }

    // Be sure to relocate EXACT PACKAGES!!
    // For example, relocate org.some.package instead of org
    // Becuase relocation org will break other non-relocated dependencies such as org.minecraft
    listOf(
        "com.fasterxml",
        "net.kyori",
        "org.h2",
        "com.neovisionaries",
        "gnu.trove",
        "org.json",
        "org.apache",
        "org.telegram",
        "okhttp3",
        "net.dv8tion",
        "okio",
        "org.slf4j",
        "kotlin",
        "kotlinx",
        "it.krzeminski",
        "net.thauvin",
        "org.jetbrains.exposed.dao",
        "org.jetbrains.exposed.exceptions",
        "org.jetbrains.exposed.sql",
        "org.jetbrains.exposed.jdbc",
        "org.jetbrains.kotlin",
        "org.jetbrains.kotlinx",
        "com.charleskorn.kaml",
        "ru.astrainteractive.klibs",
        "ru.astrainteractive.astralibs",
        "club.minnced.discord",
        "club.minnced.opus"
    ).forEach { pattern -> relocate(pattern, "${requireProjectInfo.group}.shade.$pattern") }
}
