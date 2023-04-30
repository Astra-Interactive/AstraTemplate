import org.gradle.api.file.DuplicatesStrategy

plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

tasks.processResources {
    filteringCharset = "UTF-8"
    from(sourceSets.main.get().resources.srcDirs) {
        // It is available to declare dependencies like this
        val implementations: List<String> = listOf(
            libs.kotlin.coroutines.core,
            libs.kotlin.coroutines.coreJvm,
            libs.kotlin.serialization,
            libs.kotlin.serializationJson,
            libs.kotlin.serializationKaml,
            libs.kotlin.gradle,
        ).map { it.get().toString() }
        filesMatching("plugin.yml") {
            expand(
                "main" to "${libs.versions.plugin.group.get()}.${libs.versions.plugin.name.get()}",
                "name" to libs.versions.plugin.name.get(),
                "prefix" to libs.versions.plugin.name.get(),
                "version" to libs.versions.plugin.version.get(),
                "description" to libs.versions.plugin.description.get(),
                "url" to libs.versions.plugin.url.get(),
                "author" to libs.versions.plugin.author.get(),
                "authors" to libs.versions.plugin.authors.get().split(";").joinToString("\",\""),
                "libraries" to implementations.joinToString("\",\""),
            )
        }
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
