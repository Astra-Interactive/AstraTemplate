import com.github.jengelman.gradle.plugins.shadow.ShadowBasePlugin.Companion.shadow
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.shadow
import ru.astrainteractive.gradleplugin.property.util.requireProjectInfo

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.gradleplugin.detekt")
    id("ru.astrainteractive.gradleplugin.java.version")
    alias(libs.plugins.gradle.shadow)
    alias(libs.plugins.klibs.minecraft.resource.processor)
}

dependencies {
    compileOnly(libs.driver.h2)
    compileOnly(libs.driver.jdbc)
    compileOnly(libs.driver.mysql)
    compileOnly(libs.minecraft.discordsrv)
    compileOnly(libs.minecraft.essentialsx)
    compileOnly(libs.minecraft.luckperms)
    compileOnly(libs.minecraft.paper.api)
    compileOnly(libs.minecraft.vaultapi)
    compileOnly(libs.minecraft.kyori.api)

    shadow(libs.klibs.mikro.core)
    shadow(libs.kotlin.coroutines.core)
    shadow(libs.kotlin.serialization.json)
    shadow(libs.minecraft.astralibs.command)
    shadow(libs.minecraft.astralibs.command.bukkit)
    shadow(libs.minecraft.astralibs.core)
    shadow(libs.minecraft.astralibs.core.bukkit)
    shadow(libs.minecraft.astralibs.menu.bukkit)
    shadow(libs.minecraft.bstats)

    shadow(projects.modules.api.local)
    shadow(projects.modules.api.remote)
    shadow(projects.modules.buildKonfig)
    shadow(projects.modules.core)

    testImplementation(libs.tests.kotlin.test)
}

minecraftProcessResource {
    bukkit(
        customProperties = mapOf(
            "libraries" to listOf(
                libs.driver.h2.get(),
                libs.driver.jdbc.get(),
                libs.driver.mysql.get(),
                libs.driver.mariadb.get()
            ).joinToString("\",\"", "[\"", "\"]")
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
        .resolve("plugins")
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
        if (project.name != "bukkit") {
            // Don't relocate on: [bukkit]
            add("org.h2")
        }

        add("org.intellij")
        add("org.jetbrains.annotations")
//        add("org.jetbrains.exposed") // Don't relocate on: [*]
        add("org.jetbrains.kotlinx")
        add("org.json")
        add("org.json")
//        add("org.sqlite") // Don't relocate on: [*]
        add("org.telegram")
        add("org.telegram.telegrambots")
        add("org.w3c.css")
        add("org.w3c.dom")
        add("ru.astrainteractive.astralibs")
        add("ru.astrainteractive.klibs")
        add("tomp2p.opuswrapper")
    }.forEach { pattern -> relocate(pattern, "${requireProjectInfo.group}.shade.$pattern") }
}
