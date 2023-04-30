import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import net.fabricmc.loom.task.RemapJarTask

plugins {
    id("fabric-loom")
    id("com.github.johnrengelman.shadow")
    id("fabric-resource-processor")
    id("basic-java")
}
dependencies {
    mappings("net.fabricmc:yarn:1.19.2+build.8:v2")
    minecraft(libs.minecraft.mojang.get())
    modImplementation(libs.minecraft.fabric.kotlin.get())
    modImplementation(libs.minecraft.fabric.loader.get())
    modImplementation(libs.minecraft.fabric.api.get())
    // AstraLibs
    implementation(libs.minecraft.astralibs.ktxcore)
    implementation(libs.minecraft.astralibs.orm)
    implementation(libs.minecraft.astralibs.di)
    // Kotlin
    implementation(libs.bundles.kotlin)
    // Driver
    implementation(libs.driver.jdbc)
    implementation(projects.modules.apiLocal)
    implementation(projects.modules.apiRemote)
    implementation(projects.modules.dto)
}

val shadowJar by tasks.getting(ShadowJar::class) {
    dependencies {
        // Kotlin
        include(dependency(libs.kotlin.gradle.get()))
        include(dependency(libs.minecraft.astralibs.ktxcore.get()))
        include(dependency(libs.driver.jdbc.get()))
    }
    exclude("mappings/")
    dependsOn(configurations)
    from(sourceSets.main.get().allSource)
    mergeServiceFiles()
//    minimize()
    isReproducibleFileOrder = true
    archiveClassifier.set(null as String?)
    archiveBaseName.set(libs.versions.plugin.name.get())
}

val remapJar = tasks.getByName<RemapJarTask>("remapJar") {
    dependsOn(shadowJar)
    mustRunAfter(shadowJar)
    this.input.set(shadowJar.archiveFile)
    addNestedDependencies.set(true)
    archiveBaseName.set(libs.versions.plugin.name.get())
    destinationDirectory.set(File(libs.versions.destination.fabric.get()))
}
tasks.assemble {
    dependsOn(remapJar)
}

artifacts {
    archives(remapJar)
    shadow(shadowJar)
}
