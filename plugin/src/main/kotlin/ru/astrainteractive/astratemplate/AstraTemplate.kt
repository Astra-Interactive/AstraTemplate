@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate

import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.event.GlobalEventListener
import ru.astrainteractive.astralibs.menu.event.GlobalInventoryClickEvent
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.impl.RootModuleImpl
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.getValue

/**
 * Initial class for your plugin
 */

class AstraTemplate : JavaPlugin() {
    private val rootModuleReloadable = Reloadable {
        RootModuleImpl()
    }
    private val rootModule: RootModule by rootModuleReloadable
    private val eventManager: EventManager by rootModule.eventHandlerModule
    private val commandManager by rootModule.commandManager
    private val jLogger by rootModule.logger

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        rootModule.plugin.initialize(this)
        jLogger.info("Logger enabled", "AstraTemplate")
        jLogger.warning("Warn message from logger", "AstraTemplate")
        jLogger.error("Error message", "AstraTemplate")

        GlobalEventListener.onEnable(this)
        GlobalInventoryClickEvent.onEnable(this)
        commandManager
        eventManager.onEnable(this)
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventManager.onDisable()
        runBlocking { rootModule.database.value.closeConnection() }
        HandlerList.unregisterAll(this)
        GlobalEventListener.onDisable()
        GlobalInventoryClickEvent.onDisable()
        PluginScope.close()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        rootModule.filesModule.configFile.reload()
        rootModule.configurationModule.reload()
        rootModule.translation.reload()
    }
}
