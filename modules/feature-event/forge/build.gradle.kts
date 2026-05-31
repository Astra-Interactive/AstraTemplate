plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.gradleplugin.detekt")
    id("ru.astrainteractive.gradleplugin.java.version")
}

dependencies {
    implementation(libs.klibs.kstorage)
    implementation(libs.klibs.mikro.core)
    implementation(libs.klibs.mikro.extensions)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.core.forge)

    implementation(projects.modules.featureGui.api)
    implementation(projects.modules.core)
    implementation(projects.modules.api.local)
}

dependencies {
    // We use this because forge plugin waste a lot of resources when enabled
    compileOnly(
        files(
            rootProject
                .file(".gradle")
                .resolve("mavenizer")
                .resolve("repo")
                .resolve("net")
                .resolve("minecraftforge")
                .resolve("forge")
                .resolve(libs.versions.minecraft.forgeversion.get())
                .resolve("forge-${libs.versions.minecraft.forgeversion.get()}.jar")
        )
    )
    compileOnly(libs.minecraft.brigadier)
    compileOnly(libs.minecraft.authlib)
    compileOnly(libs.minecraft.forgeversion)
    compileOnly(libs.minecraft.datafixerupper)
    compileOnly(libs.minecraft.forge.bus)
    compileOnly(libs.joml)
}
