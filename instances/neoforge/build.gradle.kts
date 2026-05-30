import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import ru.astrainteractive.gradleplugin.property.util.requireProjectInfo

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.gradleplugin.detekt")
    id("ru.astrainteractive.gradleplugin.java.version")
    alias(libs.plugins.gradle.neoforgegradle)
    alias(libs.plugins.klibs.minecraft.resource.processor)
    alias(libs.plugins.gradle.shadow)
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    shadow(libs.kotlin.coroutines.core)
    shadow(libs.minecraft.astralibs.core)
    shadow(libs.minecraft.astralibs.core.neoforge)
    shadow(libs.minecraft.astralibs.command)
    shadow(libs.kotlin.serialization.kaml)
    shadow(libs.klibs.mikro.core)
    shadow(libs.klibs.kstorage)
    shadow(libs.minecraft.kyori.plain)
    shadow(libs.minecraft.kyori.legacy)
    shadow(libs.minecraft.kyori.gson)
    shadow(projects.modules.api.local)
    shadow(projects.modules.api.remote)
    shadow(projects.modules.buildKonfig)
    shadow(projects.modules.core)
    shadow(projects.modules.featureEvent.neoforge)
    shadow(projects.modules.featureCommand)
    shadow(projects.modules.featureGui.api)
}

minecraftProcessResource {
    neoForge(
        customProperties = mapOf(
            "minecraft_version" to libs.versions.minecraft.mojang.version.get(),
            "minecraft_version_range" to listOf(libs.versions.minecraft.mojang.version.get())
                .joinToString(","),
            "neo_version" to "neo_version",
            "neo_version_range" to "[${libs.versions.minecraft.neoforgeversion.get()},)",
        )
    )
}

val shadowJar by tasks.getting(ShadowJar::class) {
    mergeServiceFiles()
    dependsOn(tasks.named<ProcessResources>("processResources"))
    configurations = listOf(project.configurations.shadow.get())
    isReproducibleFileOrder = true
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    archiveClassifier = null as String?
    archiveVersion = requireProjectInfo.versionString
    archiveBaseName = "${requireProjectInfo.name}-${project.name}"
    destinationDirectory = rootProject.layout.buildDirectory.get()
        .asFile
        .resolve(project.name)
        .resolve("mods")
        .takeIf(File::exists)
        ?: rootDir.resolve("jars")
    dependencies {
        // Dependencies
        exclude(dependency("org.jetbrains:annotations"))
        // Root
//        exclude("kotlin/**") // Use kotlin-neoforge or kotlin-forge
        exclude("_COROUTINE/**")
        exclude("DebugProbesKt.bin")
        exclude("jetty-dir.css")
        exclude("license/**")
        exclude("**LICENCE**")
        exclude("**LICENSE**")
        // Other dependencies
        exclude("club/minnced/opus/**")
        exclude("co/touchlab/stately/**")
        exclude("com/google/**")
        exclude("com/ibm/icu/**")
        exclude("com/sun/**")
        exclude("google/protobuf/**")
        exclude("io/github/**")
        exclude("io/javalin/**")
        exclude("jakarta/servlet/**")
        exclude("javax/annotation/**")
        exclude("javax/servlet/**")
        exclude("natives/**")
        exclude("net/luckperms/**")
        exclude("nl/altindag/**")
        exclude("org/bouncycastle/**")
        exclude("org/checkerframework/**")
        exclude("org/conscrypt/**")
        exclude("org/apache/batik/**")
        exclude("org/apache/xmlgraphics/**")
        exclude("org/apache/xmlcommons/**")
        exclude("org/eclipse/**")
        exclude("jdk/xml/**")
        exclude("org/w3c/**")
        exclude("tomp2p/opuswrapper/**")
        exclude("org/slf4j/**")
        exclude("javax/xml/**")
        exclude("org/xml/**")
        // META
        exclude("META-INF/**.md")
        exclude("META-INF/**.MD")
        exclude("META-INF/**.txt**")
        exclude("META-INF/**LICENCE**")
        exclude("META-INF/com.android.tools/**")
        exclude("META-INF/gradle-plugins/**")
        exclude("META-INF/imports/**")
        exclude("META-INF/kotlin-reflection.kotlin_module")
        exclude("META-INF/license/**")
        exclude("META-INF/maven/**")
        exclude("META-INF/native-image/**")
        exclude("META-INF/native/**")
        exclude("META-INF/proguard/**")
        exclude("META-INF/rewrite/**")
        exclude("META-INF/services/kotlin.reflect.**")
//        exclude("META-INF/versions/**") // Don't remove in Forge
    }

    // Be sure to relocate EXACT PACKAGES!!
    // For example, relocate org.some.package instead of org
    // Becuase relocation org will break other non-relocated dependencies such as org.minecraft
    // Don't relocate `org.jetbrains.exposed` and `kotlin`
    listOf(
        "ch.qos.logback",
        "club.minnced.discord",
        "club.minnced.opus",
        "co.touchlab.stately",
        "com.arkivanov",
        "com.charleskorn.kaml",
        "com.fasterxml",
        "com.ibm.icu",
        "com.neovisionaries",
        "dev.icerock",
        "gnu.trove",
        "google.protobuf",
        "io.github.reactivecircus",
        "it.krzeminski",
        "it.krzeminski.snakeyaml",
//        "javax.xml", // Is present
        "kotlinx",
        "net.dv8tion",
        "net.kyori",
        "net.thauvin",
        "okhttp3",
        "okio",
        "org.apache",
        "org.h2",
        "org.intellij",
        "org.jetbrains.annotations",
        "org.jetbrains.exposed", // Don't relocate
//        "org.jetbrains.kotlin", // Don't relocate
        "org.jetbrains.kotlinx",
        "org.json",
        "org.json",
//        "org.slf4j", // Is present
        "org.sqlite",
        "org.telegram",
        "org.telegram.telegrambots",
        "org.w3c.css",
        "org.w3c.dom",
//        "org.xml.sax", // Is present
        "ru.astrainteractive.astralibs",
        "ru.astrainteractive.klibs",
        "tomp2p.opuswrapper",
    ).forEach { pattern -> relocate(pattern, "${requireProjectInfo.group}.shade.$pattern") }
}

dependencies {
    compileOnly(libs.minecraft.neoforgeversion)
}
