plugins {
    java
    `java-library`
    id("org.jetbrains.kotlin.jvm")
    id("com.github.johnrengelman.shadow")
}
tasks.shadowJar {

    isReproducibleFileOrder = true
    mergeServiceFiles()
    relocate("org.bstats", libs.versions.group.get())
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    from(sourceSets.main.get().output)
    from(project.configurations.runtimeClasspath)
    minimize()
    archiveBaseName.set(libs.versions.name.get())
    destinationDirectory.set(File(libs.versions.destinationDirectoryPath.get()))
}
