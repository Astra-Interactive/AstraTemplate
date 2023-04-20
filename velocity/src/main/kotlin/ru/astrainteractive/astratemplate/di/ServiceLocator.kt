package ru.astrainteractive.astratemplate.di

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astralibs.EmpireSerializer
import ru.astrainteractive.astralibs.di.Lateinit
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.di.module
import ru.astrainteractive.astralibs.di.reloadable
import ru.astrainteractive.astralibs.filemanager.ResourceFileManager
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.plugin.Configuration
import java.nio.file.Path

object ServiceLocator {
    val injector = Lateinit<Injector>()
    val server = Lateinit<ProxyServer>()
    val logger = Lateinit<Logger>()
    val dataDirectory = Lateinit<Path>()
    val configurationFile = module {
        val dataDirectory by dataDirectory
        ResourceFileManager("config.yml", dataDirectory.toFile(), AstraTemplate::class.java)
    }
    val configuration = reloadable {
        val configurationFile by configurationFile
        EmpireSerializer.toClass<Configuration>(configurationFile.configFile) ?: Configuration()
    }
}
