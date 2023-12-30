package ru.astrainteractive.astratemplate

import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.di.impl.RootModuleImpl
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

/**
 * Initial class for your plugin
 */

class AstraTemplate : JavaPlugin() {
    private val rootModule = RootModuleImpl()
    private val jLogger by Provider {
        rootModule.coreModule.logger.value
    }
    private val lifecycles: List<Lifecycle>
        get() = listOf(
            rootModule.coreModule.lifecycle,
            rootModule.eventModule.lifecycle,
            rootModule.apiLocalModule.lifecycle,
            rootModule.commandModule.lifecycle
        )

    init {
        rootModule.bukkitModule.plugin.initialize(this)
    }

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        jLogger.info("Logger enabled", "AstraTemplate")
        jLogger.warning("Warn message from logger", "AstraTemplate")
        jLogger.error("Error message", "AstraTemplate")
        lifecycles.forEach(Lifecycle::onEnable)
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        HandlerList.unregisterAll(this)
        lifecycles.forEach(Lifecycle::onDisable)
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        lifecycles.forEach(Lifecycle::onReload)
    }
}
