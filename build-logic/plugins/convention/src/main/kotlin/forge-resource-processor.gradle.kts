import org.gradle.api.file.DuplicatesStrategy
import org.gradle.kotlin.dsl.invoke

plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

tasks.processResources {
    filteringCharset = "UTF-8"
    from(sourceSets.main.get().resources.srcDirs) {
        filesMatching("mods.toml") {
            expand(
                "{displayName}" to libs.versions.name.get(),
                "{version}" to project.version,
                "{description}" to project.description
            )
        }
        duplicatesStrategy = DuplicatesStrategy.INCLUDE
    }
}
