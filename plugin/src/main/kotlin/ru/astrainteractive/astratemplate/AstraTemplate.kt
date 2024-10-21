package ru.astrainteractive.astratemplate

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.di.impl.RootModuleImpl

/**
 * Initial class for your plugin
 */

class AstraTemplate : JavaPlugin(), Logger by JUtiltLogger("AstraTemplate") {
    private val rootModule = RootModuleImpl(this)
    private val lifecycles: List<Lifecycle>
        get() = listOf(
            rootModule.coreModule.lifecycle,
            rootModule.eventModule.lifecycle,
            rootModule.apiLocalModule.lifecycle,
            rootModule.commandModule.lifecycle
        )

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        info { "#onEnable Logger enabled" }
        warn { "#onEnable Warn message from logger" }
        error { "#onEnable Error message" }
        lifecycles.forEach(Lifecycle::onEnable)
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        lifecycles.forEach(Lifecycle::onDisable)
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        lifecycles.forEach(Lifecycle::onReload)
    }
}
