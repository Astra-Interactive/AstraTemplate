import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.gradle.kotlin.dsl.named
import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    kotlin("jvm")
    alias(libs.plugins.gradle.shadow)
    alias(libs.plugins.gradle.buildconfig)
    alias(libs.plugins.klibs.minecraft.resource.processor)
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.kaml)
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.command)

    implementation(libs.klibs.kstorage)
    implementation(libs.klibs.mikro.core)

    compileOnly(libs.minecraft.velocity.api)
    annotationProcessor(libs.minecraft.velocity.api)

    implementation(projects.modules.core)
    implementation(projects.modules.buildKonfig)
}

minecraftProcessResource {
    velocity(
        customProperties = mapOf(
            "dependencies" to listOf(
                libs.driver.h2.get(),
                libs.driver.jdbc.get(),
                libs.driver.mysql.get(),
            ).joinToString("\",\"", "[\"", "\"]")
        )
    )
}

val shadowJar = tasks.named<ShadowJar>("shadowJar")
shadowJar.configure {

    val projectInfo = requireProjectInfo
    isReproducibleFileOrder = true
    mergeServiceFiles()
    dependsOn(configurations)
    archiveClassifier.set(null as String?)

    minimize {
        exclude(dependency(libs.exposed.jdbc.get()))
        exclude(dependency(libs.exposed.dao.get()))
    }
    archiveVersion.set(projectInfo.versionString)
    archiveBaseName.set("${projectInfo.name}-velocity")
    destinationDirectory = rootDir.resolve("build")
        .resolve("velocity")
        .resolve("plugins")
        .takeIf(File::exists)
        ?: File(rootDir, "jars").also(File::mkdirs)

    relocate("org.bstats", projectInfo.group)
    listOf(
        "co.touchlab",
        "com.mysql",
        "google.protobuf",
        "io.github.reactivecircus",
        "ch.qos.logback",
        "com.charleskorn.kaml",
        "com.ibm.icu",
        "it.krzeminski.snakeyaml",
        "net.thauvin.erik",
        "okio",
        "org.apache",
        "org.intellij",
        "org.slf4j",
        "org.jetbrains.annotations",
        "ru.astrainteractive.klibs",
        "ru.astrainteractive.astralibs"
    ).forEach { pattern -> relocate(pattern, "${projectInfo.group}.$pattern") }
    listOf(
        "org.jetbrains.exposed",
        "kotlinx",
    ).forEach { pattern ->
        relocate(pattern, "${projectInfo.group}.$pattern") {
            exclude("kotlin/kotlin.kotlin_builtins")
        }
    }
}
