package ru.astrainteractive.astratemplate

import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.events.GlobalEventListener
import ru.astrainteractive.astralibs.menu.event.SharedInventoryClickEvent
import ru.astrainteractive.astralibs.utils.Singleton
import ru.astrainteractive.astralibs.utils.setupWithSpigot
import ru.astrainteractive.astratemplate.events.EventManager
import ru.astrainteractive.astratemplate.modules.ServiceLocator
import ru.astrainteractive.astratemplate.plugin.Files

/**
 * Initial class for your plugin
 */
class AstraTemplate : JavaPlugin() {
    companion object : Singleton<AstraTemplate>()

    init {
        instance = this
    }

    /**
     * Class for handling all of your events
     */
    private val eventHandler: EventManager by ServiceLocator.eventHandlerModule
    private val commandManager by ServiceLocator.commandManager

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        AstraLibs.rememberPlugin(this)
        Logger.setupWithSpigot("AstraTemplate", this)
        eventHandler.onEnable(this)
        commandManager
        GlobalEventListener.onEnable(this)
        SharedInventoryClickEvent.onEnable(this)
        Logger.log("Logger enabled", "AstraTemplate")
        Logger.warn("Warn message from logger", "AstraTemplate")
        Logger.error("Error message", "AstraTemplate")
        val customConfiguration by ServiceLocator.customConfiguration
        Logger.error("Custom configuration version: ${customConfiguration.pluginVersion.value}", "AstraTemplate")
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventHandler.onDisable()
        runBlocking { ServiceLocator.databaseModule.value.closeConnection() }
        HandlerList.unregisterAll(this)
        GlobalEventListener.onDisable()
        SharedInventoryClickEvent.onDisable()
        PluginScope.close()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        Files.configFile.reload()
        ServiceLocator.configurationModule.reload()
        ServiceLocator.translationModule.reload()
    }
}
