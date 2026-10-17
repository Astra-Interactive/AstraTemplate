import ru.astrainteractive.gradleplugin.property.util.requireProjectInfo

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("ru.astrainteractive.gradleplugin.linter")
    id("ru.astrainteractive.gradleplugin.java.version")
    alias(libs.plugins.gradle.buildconfig)
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.serialization.kaml)

    implementation(libs.minecraft.astralibs.core)
    implementation(libs.minecraft.astralibs.command)

    implementation(libs.klibs.mikro.core)
    implementation(libs.klibs.mikro.extensions)
    api(libs.klibs.kstorage)
}

buildConfig {
    className("BuildKonfig")
    packageName(requireProjectInfo.group)
    useKotlinOutput { internalVisibility = false }
    fun buildConfigStringField(name: String, value: String) {
        buildConfigField("String", name, "\"${value}\"")
    }
    buildConfigStringField("id", requireProjectInfo.name.lowercase())
    buildConfigStringField("name", requireProjectInfo.name)
    buildConfigStringField("version", requireProjectInfo.versionString)
    buildConfigStringField("url", requireProjectInfo.url)
    buildConfigStringField("description", requireProjectInfo.description)
    requireProjectInfo.developersList.forEachIndexed { i, dev ->
        buildConfigStringField("author_$i", dev.id)
    }
}
