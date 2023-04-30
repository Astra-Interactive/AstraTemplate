package ru.astrainteractive.astratemplate

import com.google.inject.Inject
import com.google.inject.Injector
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.impl.RootModuleImpl
import ru.astrainteractive.astratemplate.di.impl.VelocityModuleImpl
import java.nio.file.Path

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
    private val rootModule: RootModule by RootModuleImpl
    private val jLogger by rootModule.logger

    init {
        VelocityModuleImpl.apply {
            this.injector.initialize(injector)
            this.server.initialize(server)
            this.logger.initialize(logger)
            this.dataDirectory.initialize(dataDirectory)
        }

        jLogger.info(BuildKonfig.name, "Hello there! I made my first plugin with Velocity.")
        jLogger.info(BuildKonfig.name, "Here's your configuration: ${RootModuleImpl.configuration.value}.")
    }

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent?) {
        // Do some operation demanding access to the Velocity API here.
        // For instance, we could register an event:
//        server.eventManager.register(this, PluginListener())
    }

    fun reload() {
        with(RootModuleImpl) {
            configuration.reload()
        }
    }
}
