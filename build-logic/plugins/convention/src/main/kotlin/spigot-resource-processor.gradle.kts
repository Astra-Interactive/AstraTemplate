import org.gradle.api.file.DuplicatesStrategy

plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

tasks.processResources {
    filteringCharset = "UTF-8"
    from(sourceSets.main.get().resources.srcDirs) {
        filesMatching("plugin.yml") {
            expand(
                "name" to libs.versions.name.get(),
                "version" to libs.versions.plugin.get(),
                "description" to libs.versions.description.get()
            )
        }
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
