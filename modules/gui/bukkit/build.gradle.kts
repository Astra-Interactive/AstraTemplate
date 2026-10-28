plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.klibs.rules.gradle.linter")
    id("ru.astrainteractive.gradleplugin.java.version")
}

configurations.api {
    dependencies.removeIf { dependency ->
        dependency.group == "org.jetbrains.kotlin" && dependency.name == "kotlin-stdlib"
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    compileOnly(libs.minecraft.paper.api)
    compileOnly(libs.minecraft.vaultapi)

    implementation(libs.klibs.kstorage)
    implementation(libs.klibs.mikro.core)
    implementation(libs.klibs.mikro.extensions)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.minecraft.astralibs.menu.bukkit)
    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.core.bukkit)

    implementation(projects.modules.gui.api)
    implementation(projects.modules.core)
    implementation(projects.modules.data.dao)

    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.minecraft.paper.api)
    testImplementation(libs.tests.kotlin.test)
}
