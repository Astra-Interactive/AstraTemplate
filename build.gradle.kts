import java.util.Properties
import java.io.FileInputStream
object Kotlin {
    const val version = "1.7.0"
    const val coroutines = "1.6.3"
    const val json = "1.3.3"
    const val kaml = "0.46.0"
}
object Spigot {
    const val version = "1.19-R0.1-SNAPSHOT"
    const val placeholderAPI = "2.11.2"
    const val protocolLib = "4.8.0"
    const val worldGuard = "7.0.7"
    const val vault = "1.7"
    const val coreProtect = "21.2"
    const val modelEngine = "R2.5.0"
    const val essentials = "2.19.5-SNAPSHOT"
    const val discordSRV = "1.25.0"
    const val luckPerms = "5.4"
}

group = "com.astrainteractive"
version = "2.1.0"
val name = "AstraTemplate"
description = "Template plugin from AstraInteractive"

plugins {
    java
    `maven-publish`
    `java-library`
    kotlin("jvm") version "1.7.0"
    kotlin("plugin.serialization") version "1.7.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}
java {
    withSourcesJar()
    withJavadocJar()
    java.sourceCompatibility = JavaVersion.VERSION_1_8
    java.targetCompatibility = JavaVersion.VERSION_17
}
repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://nexus.scarsz.me/content/groups/public/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.essentialsx.net/snapshots/")
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://repo.maven.apache.org/maven2/")
    maven("https://maven.enginehub.org/repo/")
    maven("https://repo1.maven.org/maven2/")
    maven("https://m2.dv8tion.net/releases")
    maven("https://maven.playpro.com")
    maven("https://jitpack.io")
    maven {
        url = uri("https://mvn.lumine.io/repository/maven-public/")
        metadataSources {
            artifact()
        }
    }
    flatDir { dirs("libs") }
}

dependencies {
    // Kotlin
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Kotlin.coroutines}")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Kotlin.coroutines}")
    // Serialization
    implementation("org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.json}")
    implementation("com.charleskorn.kaml:kaml:${Kotlin.kaml}")
    // AstraLibs
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))


    // Spigot dependencies
    compileOnly("net.essentialsx:EssentialsX:${Spigot.essentials}")
    compileOnly("io.papermc.paper:paper-api:${Spigot.version}")
    compileOnly("org.spigotmc:spigot-api:${Spigot.version}")
    compileOnly("org.spigotmc:spigot:${Spigot.version}")
    compileOnly("com.comphenix.protocol:ProtocolLib:${Spigot.protocolLib}")
    compileOnly("me.clip:placeholderapi:${Spigot.placeholderAPI}")
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:${Spigot.worldGuard}")
    compileOnly("com.discordsrv:discordsrv:${Spigot.discordSRV}")
    compileOnly("com.github.MilkBowl:VaultAPI:${Spigot.vault}")
    compileOnly("net.coreprotect:coreprotect:${Spigot.coreProtect}")
    compileOnly("com.ticxo.modelengine:api:${Spigot.modelEngine}")
    // Test
    testImplementation("junit:junit:4.13.1")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:1.24.1")
    testImplementation("io.kotest:kotest-runner-junit5:latest.release")
    testImplementation("io.kotest:kotest-assertions-core:latest.release")
    testImplementation(kotlin("test"))
}



tasks {
    withType<JavaCompile>() {
        options.encoding = "UTF-8"
    }
    withType<Test>().configureEach {
        useJUnitPlatform()
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions.jvmTarget = "17"
    }
    withType<Jar> {
        archiveClassifier.set("min")
    }
    compileJava {
        options.encoding = "UTF-8"
    }
    test {
        useJUnit()
        testLogging {
            events("passed", "skipped", "failed")
            this.showStandardStreams = true
        }
    }
    processResources {
        from(sourceSets.main.get().resources.srcDirs) {
            filesMatching("plugin.yml") {
                expand(
                    "name" to project.name,
                    "version" to project.version,
                    "description" to project.description
                )
            }
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}
tasks.shadowJar {
    dependencies {
        include(dependency(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar", ".aar")))))
        include(dependency("org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"))
        include(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:${Kotlin.coroutines}"))
        include(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Kotlin.coroutines}"))
        include(dependency("org.jetbrains.kotlin:kotlin-serialization:${Kotlin.version}"))
        include(dependency("org.jetbrains.kotlinx:kotlinx-serialization-json:${Kotlin.json}"))
        include(dependency("com.charleskorn.kaml:kaml:${Kotlin.kaml}"))
    }
    isReproducibleFileOrder = true
    mergeServiceFiles()
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    from(sourceSets.main.get().output)
    from(project.configurations.runtimeClasspath)
    minimize()
    destinationDirectory.set(File("D:\\Minecraft Servers\\TEST_SERVER\\plugins"))
}
