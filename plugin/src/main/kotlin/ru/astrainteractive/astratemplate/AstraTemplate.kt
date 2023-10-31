package ru.astrainteractive.astratemplate

import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astratemplate.di.impl.RootModuleImpl
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

/**
 * Initial class for your plugin
 */

class AstraTemplate : JavaPlugin() {
    private val rootModule = RootModuleImpl()
    private val jLogger by Provider {
        rootModule.sharedModule.logger.value
    }

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

        rootModule.bukkitModule.eventListener.value.onEnable(this)
        rootModule.bukkitModule.inventoryClickEvent.value.onEnable(this)
        rootModule.bukkitModule.eventManager.value.onEnable(this)
        rootModule.bukkitModule.commandManager.value
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        rootModule.bukkitModule.eventManager.value.onDisable()
        runBlocking { rootModule.apiLocalModule.database.closeConnection() }
        HandlerList.unregisterAll(this)
        rootModule.bukkitModule.eventListener.value.onDisable()
        rootModule.bukkitModule.inventoryClickEvent.value.onDisable()
        rootModule.sharedModule.pluginScope.value.close()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        rootModule.sharedModule.configurationModule.reload()
        rootModule.sharedModule.translation.reload()
    }
}
