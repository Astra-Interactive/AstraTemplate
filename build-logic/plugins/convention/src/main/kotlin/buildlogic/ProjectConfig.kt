package buildlogic

import libs
import org.gradle.api.Project

object ProjectConfig {
    val Project.info: ProjectInfo
        get() = DefaultProjectInfo(
            id = libs.versions.name.get().toLowerCase(),
            name = libs.versions.name.get(),
            version = libs.versions.plugin.get(),
            url = libs.versions.url.get(),
            description = libs.versions.description.get(),
            authors = listOf("Roman Makeev"),
            main = "${libs.versions.group.get()}.${libs.versions.name.get()}"
        )
}
