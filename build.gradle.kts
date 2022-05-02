import java.util.Properties
import java.io.FileInputStream

var gprUser: String? = null
var gprPassword: String? = null
var versionBuildRelease = 0

plugins {
    java
    `maven-publish`
    `java-library`
    kotlin("jvm") version "1.5.21"
    id("com.github.johnrengelman.shadow") version "7.1.0"
}
fun getFileOrCreate(path: String): File {
    val _file = file(path)
    if (!_file.exists()) _file.createNewFile()
    return _file
}

inline fun <reified T> File.getProperty(key: String, default: T? = null): T? {
    val prop: String = Properties().apply { load(inputStream()) }.getProperty(key) ?: return default
    return when (T::class) {
        Int::class -> prop.toIntOrNull() as T?
        String::class -> prop as T?
        else -> throw IllegalStateException("Unknown Generic Type")
    }
}

inline fun <reified T> File.setProperty(key: String, value: T? = null) {
    Properties().apply {
        load(inputStream())
        this.setProperty(key, value?.toString())
    }.store(this.outputStream(), "")

}

task("Get GPR user keys") {
    val astraPropsFile = getFileOrCreate("astra.properties")
    gprUser = astraPropsFile.getProperty<String?>("gpr.user")
    gprPassword = astraPropsFile.getProperty<String?>("gpr.password")
    if (gprUser == null || gprPassword == null) {
        astraPropsFile.setProperty("gpr.user", gprUser ?: "SET_GPR_USERNAME_HERE")
        astraPropsFile.setProperty("gpr.password", gprPassword ?: "SET_GPR_KEY_HERE")
        throw GradleException("You need to set your GPR keys")
    }
}
task("Get versionBuildRelease") {
    val versionPropsFile = getFileOrCreate("version.properties")
    versionBuildRelease = versionPropsFile.getProperty("VERSION_BUILD_RELEASE") ?: 0
    versionPropsFile.setProperty("VERSION_BUILD_RELEASE", ++versionBuildRelease)
}
group = "com.astrainteractive"
version = "2.1.$versionBuildRelease"
val name = "AstraTemplate"
description = "Template plugin from AstraInteractive"
java.sourceCompatibility = JavaVersion.VERSION_17
repositories {
    mavenLocal()
    mavenCentral()
    maven("https://repo.maven.apache.org/maven2/")
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/Astra-Interactive/AstraLibs")
        credentials {
            username = gprUser
            password = gprPassword
        }
        metadataSources {
            artifact()
        }
    }
    maven("https://repo1.maven.org/maven2/")
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://maven.enginehub.org/repo/")
}

dependencies {
    implementation("com.astrainteractive:astralibs:1.1.9-8")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.1")
    compileOnly("io.papermc.paper:paper-api:1.18.1-R0.1-SNAPSHOT")
    compileOnly("com.comphenix.protocol:ProtocolLib:4.7.0")
    compileOnly("me.clip:placeholderapi:2.10.9")
    testImplementation("junit:junit:4.13.1")
    testImplementation("com.github.seeseemelk:MockBukkit-v1.18:1.24.1")
    testImplementation("io.kotest:kotest-runner-junit5:latest.release")
    testImplementation("io.kotest:kotest-assertions-core:latest.release")
    testImplementation(kotlin("test"))
}



publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.withType<JavaCompile>() {
    options.encoding = "UTF-8"
}
tasks.withType<Test> {
    useJUnitPlatform()
}
tasks {
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

    compileJava {
        options.encoding = "UTF-8"
    }
    shadowJar {
        dependencies {
            include(dependency("com.astrainteractive:astralibs:1.1.9-8"))
            include(dependency("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.21"))
            include(dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.6.10"))
            include(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.1"))
            include(dependency("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.6.1"))
        }

        from(sourceSets.main.get().output)
        minimize()
    }
}