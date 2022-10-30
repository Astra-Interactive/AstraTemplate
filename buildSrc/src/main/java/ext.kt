import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.file.DuplicatesStrategy
import org.gradle.kotlin.dsl.TaskContainerScope
import java.io.InputStream
import java.util.*

fun RepositoryHandler.astraLibs(project: Project) {
    maven {
        url = project.uri("https://maven.pkg.github.com/Astra-Interactive/AstraLibs")
        val config = project.getConfig()
        credentials {
            username = config.username
            password = config.token
        }
    }
}
fun RepositoryHandler.modelEngige(project: Project) {
    maven {
        url = project.uri("https://mvn.lumine.io/repository/maven-public/")
        metadataSources {
            artifact()
        }
    }
}
fun RepositoryHandler.paperMC(project: Project) {
    maven {
        url = project.uri("https://repo.papermc.io/repository/maven-public/")
    }
}

fun Project.getPluginProperties(path: String): Properties {
    val properties: Properties = Properties()
    val inputStream: InputStream = rootProject.file(path).inputStream()
    properties.load(inputStream)
    return properties
}

data class Config(
    val username: String,
    val password: String,
    val token: String
)

fun Project.getConfig(): Config {
    val properties = this.getPluginProperties("astra.properties")
    return Config(
        properties.getProperty("username") ?: "",
        properties.getProperty("password") ?: "",
        properties.getProperty("token") ?: "",
    )
}