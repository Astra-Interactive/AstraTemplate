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
import ru.astrainteractive.astratemplate.di.FilesModule
import ru.astrainteractive.astratemplate.di.LocalApiModuleImpl
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.events.EventManager

/**
 * Initial class for your plugin
 */

class AstraTemplate : JavaPlugin() {
    init {
        RootModule.plugin.initialize(this)
    }

    private val eventManager: EventManager by RootModule.eventHandlerModule
    private val commandManager by RootModule.commandManager
    private val jLogger by RootModule.logger

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        eventManager.onEnable(this)
        commandManager
        jLogger.info("Logger enabled", "AstraTemplate")
        jLogger.warning("Warn message from logger", "AstraTemplate")
        jLogger.error("Error message", "AstraTemplate")
        val customConfiguration by RootModule.customConfiguration
        jLogger.info("Custom configuration version: ${customConfiguration.pluginVersion.value}", "AstraTemplate")

        GlobalEventListener.onEnable(this)
        GlobalInventoryClickEvent.onEnable(this)
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
        FilesModule.configFile.value.reload()
        RootModule.configurationModule.reload()
        RootModule.translationModule.reload()
    }
}
