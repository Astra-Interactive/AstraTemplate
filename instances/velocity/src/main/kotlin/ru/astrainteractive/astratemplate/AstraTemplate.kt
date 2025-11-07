@file:Suppress("UnusedPrivateMember")

package ru.astrainteractive.astratemplate

import com.google.inject.Inject
import com.google.inject.Injector
import com.velocitypowered.api.event.Subscribe
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent
import com.velocitypowered.api.plugin.Plugin
import com.velocitypowered.api.plugin.annotation.DataDirectory
import com.velocitypowered.api.proxy.ProxyServer
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.api.VelocityCommandRegistryContext
import ru.astrainteractive.astratemplate.command.reload.ReloadCommandRegistry
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import java.nio.file.Path
import org.slf4j.Logger as Slf4jLogger

@Plugin(
    id = BuildKonfig.id,
    name = BuildKonfig.name,
    version = BuildKonfig.version,
    url = BuildKonfig.url,
    description = BuildKonfig.description,
    authors = [BuildKonfig.author_0],
    dependencies = []
)
class AstraTemplate @Inject constructor(
    private val injector: Injector,
    private val server: ProxyServer,
    private val logger: Slf4jLogger,
    @DataDirectory
    private val dataDirectory: Path
) : Logger by JUtiltLogger("AstraTemplate").withoutParentHandlers() {
    private val rootModule = RootModule()
    private val lifecycles: List<Lifecycle>
        get() = listOf(
            rootModule.coreModule.lifecycle,
        )

    @Subscribe
    fun onProxyInitialization(event: ProxyInitializeEvent?) {
        // Do some operation demanding access to the Velocity API here.
        // For instance, we could register an event:
        rootModule.velocityModule.apply {
            this.injector = (this@AstraTemplate.injector)
            this.server = (this@AstraTemplate.server)
            this.logger = (this@AstraTemplate.logger)
            this.dataDirectory = (this@AstraTemplate.dataDirectory)
            this.plugin = (this@AstraTemplate)
        }

        info { "Hello there! I made my first plugin with Velocity" }
        info { "Here's your configuration: ${rootModule.coreModule.configKrate}." }

        ReloadCommandRegistry(
            registryContext = VelocityCommandRegistryContext(
                proxyServer = rootModule.velocityModule.server,
                plugin = rootModule.velocityModule.plugin
            )
        ).register()
    }

    fun reload() {
        lifecycles.forEach(Lifecycle::onReload)
    }
}
