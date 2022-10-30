import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.support.delegates.ProjectDelegate
import java.io.InputStream
import java.net.URI
import java.util.*

object Dependencies {
    const val version = "3.0.0"
    const val group = "com.astrainteractive"
    const val destinationDirectoryPath = "D:\\Minecraft Servers\\TEST_SERVER\\plugins"

    object Kotlin {
        const val version = "1.7.0"
        const val coroutines = "1.6.3"
        const val json = "1.3.3"
        const val kaml = "0.46.0"
        const val exposed = "0.38.1"
        const val jdbc = "3.36.0.3"
        const val astraLibs = "2.0.7"
        const val shadow = "7.1.2"
        const val mysqlDriver = "8.0.20"
        const val orgTesting = "7.1.0"
    }

    object Spigot {
        const val velocity = "3.1.1"
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
        const val bstats = "3.0.0"
    }

    object Repositories {
        const val lumine = "https://mvn.lumine.io/repository/maven-public/"
        const val jitpack = "https://jitpack.io"
        const val clojars = "https://repo.clojars.org/"
        const val playpro = "https://maven.playpro.com"
        const val dv8tion = "https://m2.dv8tion.net/releases"
        const val maven2 = "https://repo1.maven.org/maven2/"
        const val enginehub = "https://maven.enginehub.org/repo/"
        const val maven2Apache = "https://repo.maven.apache.org/maven2/"
        const val essentialsx = "https://repo.essentialsx.net/snapshots/"
        const val dmulloy2 = "https://repo.dmulloy2.net/repository/public/"
        const val scarsz = "https://nexus.scarsz.me/content/groups/public/"
        const val papermc = "https://papermc.io/repo/repository/maven-public/"
        const val spigotmc = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/"

        /**
         * PlaceholderAPI
         */
        const val extendedclip = "https://repo.extendedclip.com/content/repositories/placeholderapi/"

    }

    object Libraries {
        // Kotlin
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

        // Coroutines
        const val kotlinxCoroutinesCore =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Dependencies.Kotlin.coroutines}"
        const val kotlinxCoroutinesCoreJVM =
            "org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:${Dependencies.Kotlin.coroutines}"

        // Serialization
        const val kotlinxSerialization = "org.jetbrains.kotlin:kotlin-serialization:${Dependencies.Kotlin.version}"
        const val kotlinxSerializationJson =
            "org.jetbrains.kotlinx:kotlinx-serialization-json:${Dependencies.Kotlin.json}"
        const val kotlinxSerializationYaml = "com.charleskorn.kaml:kaml:${Dependencies.Kotlin.kaml}"

        // AstraLibs
        const val astraLibsKtxCore = "ru.astrainteractive.astralibs:ktx-core:${Dependencies.Kotlin.astraLibs}"
        const val astraLibsSpigotCore = "ru.astrainteractive.astralibs:spigot-core:${Dependencies.Kotlin.astraLibs}"

        // Exposed
        const val jdbc = "org.xerial:sqlite-jdbc:${Kotlin.jdbc}"
        const val exposedJavaTime = "org.jetbrains.exposed:exposed-java-time:${Kotlin.exposed}"
        const val exposedJDBC = "org.jetbrains.exposed:exposed-jdbc:${Kotlin.exposed}"
        const val exposedDAO = "org.jetbrains.exposed:exposed-dao:${Kotlin.exposed}"
        const val exposedCORD = "org.jetbrains.exposed:exposed-core:${Kotlin.exposed}"
        const val mysqlConnectorJava = "mysql:mysql-connector-java:${Dependencies.Kotlin.mysqlDriver}"

        // Testing
        const val orgTeting = "org.testng:testng:${Dependencies.Kotlin.orgTesting}"

        // Spigot
        const val essentialsX = "net.essentialsx:EssentialsX:${Dependencies.Spigot.essentials}"
        const val paperMC = "io.papermc.paper:paper-api:${Dependencies.Spigot.version}"
        const val spigotApi = "org.spigotmc:spigot-api:${Dependencies.Spigot.version}"
        const val spigot = "org.spigotmc:spigot:${Dependencies.Spigot.version}"
        const val velocityApi = "com.velocitypowered:velocity-api:${Dependencies.Spigot.velocity}"
        const val protocolLib = "com.comphenix.protocol:ProtocolLib:${Dependencies.Spigot.protocolLib}"
        const val placeholderapi = "me.clip:placeholderapi:${Dependencies.Spigot.placeholderAPI}"
        const val worldguard = "com.sk89q.worldguard:worldguard-bukkit:${Dependencies.Spigot.worldGuard}"
        const val discordsrv = "com.discordsrv:discordsrv:${Dependencies.Spigot.discordSRV}"
        const val vaultAPI = "com.github.MilkBowl:VaultAPI:${Dependencies.Spigot.vault}"
        const val coreprotect = "net.coreprotect:coreprotect:${Dependencies.Spigot.coreProtect}"
        const val modelengine = "com.ticxo.modelengine:api:${Dependencies.Spigot.modelEngine}"
        const val bstats = "org.bstats:bstats-bukkit:${Dependencies.Spigot.bstats}"
    }
}