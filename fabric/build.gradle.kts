import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask
import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo
import ru.astrainteractive.gradleplugin.setupFabricProcessor

plugins {
    kotlin("jvm")
    id("fabric-loom")
    id("com.github.johnrengelman.shadow")
    alias(libs.plugins.klibs.gradle.java.core)
}

dependencies {
    mappings("net.fabricmc:yarn:${libs.versions.minecraft.fabric.yarn.get()}:v2")
    minecraft(libs.minecraft.mojang.get())
    modImplementation(libs.minecraft.fabric.kotlin.get())
    modImplementation(libs.minecraft.fabric.loader.get())
    modImplementation(libs.minecraft.fabric.api.get())
    // AstraLibs
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.klibs.kdi)
    implementation(libs.klibs.kdi)
    implementation(libs.minecraft.astralibs.command)
    // klibs
    implementation(libs.klibs.mikro.core)
    // Kotlin
    implementation(libs.bundles.kotlin)
    // Driver
    implementation(libs.driver.jdbc)
    // Local
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.core)
}

val destination = File("C:\\Users\\Roman\\Desktop\\Fabric\\mods")
    .takeIf(File::exists)
    ?: File(rootDir, "jars")

val shadowJar by tasks.getting(ShadowJar::class) {
    dependencies {
        // Kotlin
        include(dependency(libs.minecraft.astralibs.core.asProvider().get()))
        include(dependency(projects.modules.core))
        include(dependency(projects.modules.apiRemote))
        include(dependency(projects.modules.apiLocal))
        // TODO somehow fix the multiplatform libraries
        include(dependency("ru.astrainteractive.klibs:kdi-jvm"))
        include(dependency("ru.astrainteractive.klibs:mikro-core-jvm"))
        include(dependency(libs.driver.jdbc.get()))
    }
    exclude("mappings/")
    dependsOn(configurations)
    from(sourceSets.main.get().allSource)
    mergeServiceFiles()
    isReproducibleFileOrder = true
    archiveClassifier.set(null as String?)
    archiveBaseName.set("${requireProjectInfo.name}-fabric-shadow")
}

val remapJar = tasks.getByName<RemapJarTask>("remapJar") {
    dependsOn(shadowJar)
    mustRunAfter(shadowJar)
    inputFile = shadowJar.archiveFile
    addNestedDependencies.set(true)
    archiveBaseName.set("${requireProjectInfo.name}-fabric-remap")
    destinationDirectory.set(destination)
}

tasks.assemble {
    dependsOn(remapJar)
}

artifacts {
    archives(remapJar)
    shadow(shadowJar)
}

setupFabricProcessor()
