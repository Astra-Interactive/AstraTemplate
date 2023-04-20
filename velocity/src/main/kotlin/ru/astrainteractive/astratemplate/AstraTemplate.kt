package ru.astrainteractive.astratemplate

import com.astrainteractive.astratemplate.BuildKonfig
import com.google.inject.Inject
import com.google.inject.Injector
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astratemplate.di.ServiceLocator
import java.nio.file.Path
import kotlin.io.path.absolutePathString

@Plugin(
    id = BuildKonfig.id,
    name = BuildKonfig.name,
    version = BuildKonfig.version,
    url = BuildKonfig.url,
    description = BuildKonfig.description,
    authors = [BuildKonfig.author],
    dependencies = []
)
class AstraTemplate @Inject constructor(
    injector: Injector,
    server: ProxyServer,
    logger: Logger,
    @DataDirectory dataDirectory: Path
) {
    init {
        ru.astrainteractive.astralibs.Logger.logger = java.util.logging.Logger.getAnonymousLogger()
        ru.astrainteractive.astralibs.Logger.logsFolderPath = dataDirectory.absolutePathString()
        ServiceLocator.injector.initialize(injector)
        ServiceLocator.server.initialize(server)
        ServiceLocator.logger.initialize(logger)
        ServiceLocator.dataDirectory.initialize(dataDirectory)
        logger.info("Hello there! I made my first plugin with Velocity.")
        logger.info("Here's your configuration: ${ServiceLocator.configuration.value}.")
    }

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent?) {
        // Do some operation demanding access to the Velocity API here.
        // For instance, we could register an event:
//        server.eventManager.register(this, PluginListener())
    }

    fun reload() {
        with(ServiceLocator) {
            configuration.reload()
        }
    }
}
