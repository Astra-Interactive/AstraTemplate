package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.Reloadable
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.filemanager.impl.JVMResourceFileManager
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astralibs.logging.JUtilLogger
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.VelocityModule
import ru.astrainteractive.astratemplate.plugin.Configuration

object RootModuleImpl : RootModule {

    override val velocityModule: VelocityModule by VelocityModuleImpl

    override val logger = Single {
        val dataDirectory by velocityModule.dataDirectory
        JUtilLogger("AstraTemplate", dataDirectory.toFile())
    }
    override val configurationFile = Single {
        val dataDirectory by velocityModule.dataDirectory
        JVMResourceFileManager("config.yml", dataDirectory.toFile(), AstraTemplate::class.java)
    }
    override val configuration = Reloadable {
        val configurationFile by configurationFile
        ConfigLoader.toClassOrDefault(configurationFile.configFile, ::Configuration)
    }
}
