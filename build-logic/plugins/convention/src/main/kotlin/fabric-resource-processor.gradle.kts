

plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
}

tasks.processResources {
    filteringCharset = "UTF-8"
    inputs.property("version", libs.versions.plugin.get())
    filesMatching("fabric.mod.json") {
        expand(mutableMapOf("version" to libs.versions.plugin.get()))
    }
}
