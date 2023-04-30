@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate

import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.events.GlobalEventListener
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astralibs.menu.event.GlobalInventoryClickEvent
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.impl.FilesModuleImpl
import ru.astrainteractive.astratemplate.di.impl.LocalApiModuleImpl
import ru.astrainteractive.astratemplate.di.impl.PluginModuleImpl
import ru.astrainteractive.astratemplate.di.impl.RootModuleImpl
import ru.astrainteractive.astratemplate.events.EventManager

/**
 * Initial class for your plugin
 */

class AstraTemplate : JavaPlugin() {

    init {
        PluginModuleImpl.plugin.initialize(this)
    }
    private val rootModule: RootModule by RootModuleImpl
    private val eventManager: EventManager by rootModule.eventHandlerModule
    private val commandManager by rootModule.commandManager
    private val jLogger by rootModule.pluginModule.logger

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        jLogger.info("Logger enabled", "AstraTemplate")
        jLogger.warning("Warn message from logger", "AstraTemplate")
        jLogger.error("Error message", "AstraTemplate")

        val customConfiguration by RootModuleImpl.customConfiguration
        jLogger.info("Custom configuration version: ${customConfiguration.pluginVersion.value}", "AstraTemplate")
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
        runBlocking { LocalApiModuleImpl.database.value.closeConnection() }
        HandlerList.unregisterAll(this)
        GlobalEventListener.onDisable()
        GlobalInventoryClickEvent.onDisable()
        PluginScope.close()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        FilesModuleImpl.configFile.value.reload()
        RootModuleImpl.configurationModule.reload()
        RootModuleImpl.translationModule.reload()
    }
}
