package ru.astrainteractive.astratemplate.di

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astralibs.Lateinit
import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Reloadable
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.filemanager.impl.JVMResourceFileManager
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astralibs.logging.JUtilLogger
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.plugin.Configuration
import java.nio.file.Path

object ServiceLocator : Module {
    object VelocityModule : Module {
        val injector = Lateinit<Injector>()
        val server = Lateinit<ProxyServer>()
        val logger = Lateinit<Logger>()
        val dataDirectory = Lateinit<Path>()
    }

    val logger = Single {
        val dataDirectory by VelocityModule.dataDirectory
        JUtilLogger("AstraTemplate", dataDirectory.toFile())
    }
    val configurationFile = Single {
        val dataDirectory by VelocityModule.dataDirectory
        JVMResourceFileManager("config.yml", dataDirectory.toFile(), AstraTemplate::class.java)
    }
    val configuration = Reloadable {
        val configurationFile by configurationFile
        ConfigLoader.toClassOrDefault(configurationFile.configFile, ::Configuration)
    }
}
