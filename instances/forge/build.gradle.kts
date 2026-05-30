import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.minecraftforge.jarjar.gradle.JarJar
import net.minecraftforge.jarjar.gradle.JarJarDependencyMethods
import ru.astrainteractive.gradleplugin.property.util.requireProjectInfo

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.gradleplugin.detekt")
    id("ru.astrainteractive.gradleplugin.java.version")
    alias(libs.plugins.gradle.forgegradle)
    alias(libs.plugins.gradle.forgerenamer)
    alias(libs.plugins.gradle.shadow)
    alias(libs.plugins.klibs.minecraft.resource.processor)
    alias(libs.plugins.gradle.forge.jarjar)
}

repositories {
    minecraft.mavenizer(this)
    mavenCentral()
    mavenLocal()
    maven(fg.forgeMaven)
    maven(fg.minecraftLibsMaven)
}

dependencies {
    shadow(libs.kotlin.coroutines.core)
    shadow(libs.minecraft.astralibs.core)
    shadow(libs.minecraft.astralibs.core.forge)
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
    shadow(projects.modules.featureEvent.forge)
    shadow(projects.modules.featureCommand)
}

minecraftProcessResource {
    forge(
        customProperties = mapOf(
            "minecraft_version" to libs.versions.minecraft.forgeversion.get().split("-")[0],
            "forge_version" to libs.versions.minecraft.forgeversion.get().split("-")[1],
            "mod_license" to "MIT License"
        )
    )
}

jarJar {
    register()
}

val shadowJar by tasks.getting(ShadowJar::class) {
    mergeServiceFiles()
    dependsOn(tasks.named<ProcessResources>("processResources"))
    dependsOn(tasks.withType<JarJar>())
    from(
        tasks.withType<JarJar>()
            .map { task -> task.archiveFile }
            .map { archiveFile -> project.zipTree(archiveFile) },
        { include("META-INF/jarjar/**") }
    )

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
        exclude("ch/qos/logback/**")
        exclude("com/ibm/icu/**")
        exclude("it/unimi/dsi/**")
        exclude("javax/**")
        exclude("mozilla/**")
        exclude("org/apache/batik/**")
        exclude("org/apache/commons/logging/**")
        exclude("org/apache/xmlgraphics/**")
        exclude("org/intellij/lang/annotations/**")
        exclude("org/jetbrains/annotations/**")
        exclude("org/slf4j/**")
        exclude("org/w3c/dom/**")
        exclude("org/jspecify/annotations/**")
        // Root
        if (project.name == "forge" || project.name == "neoforge") {
            // Use kotlin-neoforge or kotlin-forge
            exclude("kotlin/**")
        }
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
        exclude("org/sqlite/**")
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
        if (project.name != "forge") {
            // Don't remove in: [forge]
            exclude("META-INF/versions/**")
        }
        // DEPENDENCIES
        if (project.name == "bukkit") {
            exclude(dependency("com.fasterxml.jackson.core:.*"))
            exclude(dependency("com.google.code.gson:.*"))
            exclude(dependency("com.google.crypto.tink:.*"))
            exclude(dependency("com.google.errorprone:.*"))
            exclude(dependency("com.mojang:brigadier"))
            exclude(dependency("com.mysql:mysql-connector-j"))
            exclude(dependency("mysql:mysql-connector-java"))
            exclude(dependency("net.java.dev.jna:.*"))
            exclude(dependency("net.kyori:.*"))
            exclude(dependency("org.apache.xmlgraphics:.*"))
            exclude(dependency("org.bouncycastle:.*"))
            exclude(dependency("org.checkerframework:.*"))
            exclude(dependency("org.conscrypt:.*"))
            exclude(dependency("org.eclipse.jetty.toolchain:.*"))
            exclude(dependency("org.eclipse.jetty:.*"))
            exclude(dependency("org.xerial:sqlite-jdbc"))
        }
    }

    relocate("org.bstats", requireProjectInfo.group)
    // Be sure to relocate EXACT PACKAGES!!
    // For example, relocate org.some.package instead of org
    // Becuase relocation org will break other non-relocated dependencies such as org.minecraft
    // Don't relocate `org.jetbrains.exposed` and `kotlin`
    buildList {
        add("ch.qos.logback")
        add("club.minnced.discord")
        add("club.minnced.opus")
        add("co.touchlab.stately")
        add("com.arkivanov")
        add("com.charleskorn.kaml")
        if (project.name != "bukkit") {
            // Don't relocate on: [bukkit]
            add("com.fasterxml")
        }
        add("com.ibm.icu")
        add("com.neovisionaries")
        add("dev.icerock")
        add("gnu.trove")
        add("google.protobuf")
        add("io.github.reactivecircus")
        add("it.krzeminski")
        add("it.krzeminski.snakeyaml")
        if (project.name != "bukkit") {
            // Is present on: [bukkit]
            add("javax.xml")
        }
        add("kotlinx")
        add("net.dv8tion")
        if (project.name != "bukkit") {
            // Don't relocate on: [bukkit]
            add("net.kyori")
        }
        add("net.thauvin")
        add("okhttp3")
        add("okio")
        add("org.apache")
        add("org.intellij")
        add("org.jetbrains.annotations")
        add("org.jetbrains.exposed") // Don't relocate on: [*]
        add("org.jetbrains.kotlinx")
        add("org.json")
        add("org.json")
        add("org.telegram")
        add("org.telegram.telegrambots")
        add("org.w3c.css")
        add("org.w3c.dom")
        add("ru.astrainteractive.astralibs")
        add("ru.astrainteractive.klibs")
        add("tomp2p.opuswrapper")
    }.forEach { pattern -> relocate(pattern, "${requireProjectInfo.group}.shade.$pattern") }
}

minecraft {
    mappings("official", libs.versions.minecraft.mojang.version.get())
    useDefaultAccessTransformer()
}

dependencies {
    compileOnly(minecraft.dependency(libs.minecraft.forgeversion.get()))
    "jarJar"(libs.driver.jdbc) {
        JarJarDependencyMethods.getJarJar(this).setVersion(libs.versions.driver.jdbc)
    }
    "jarJar"(libs.driver.h2) {
        JarJarDependencyMethods.getJarJar(this).setVersion(libs.versions.driver.h2)
    }
}

renamer {
    mappings.from(minecraft.dependency.toSrgFile)
}

val reobfShadowJar by renamer.classes(tasks.named<Jar>("shadowJar")) {
    output = input
}

shadowJar.finalizedBy(reobfShadowJar)
reobfShadowJar.mustRunAfter(shadowJar)
