
import ru.astrainteractive.gradleplugin.util.ProjectProperties.projectInfo


plugins {
    kotlin("jvm")
    alias(libs.plugins.gradle.buildconfig)
}

buildConfig {
    className("BuildKonfig")
    packageName(projectInfo.group)
    fun buildConfigStringField(name: String, value: String) {
        buildConfigField("String", name, "\"${value}\"")
    }
    buildConfigStringField("id", projectInfo.name.lowercase())
    buildConfigStringField("name", projectInfo.name)
    buildConfigStringField("version", projectInfo.versionString)
    buildConfigStringField("url", projectInfo.url)
    buildConfigStringField("description", projectInfo.description)
    projectInfo.developersList.forEachIndexed { i, dev ->
        buildConfigStringField("author_$i", dev.id)
    }
}
