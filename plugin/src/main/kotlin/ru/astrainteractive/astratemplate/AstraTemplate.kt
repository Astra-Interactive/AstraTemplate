package ru.astrainteractive.astratemplate

import CommandManager
import kotlinx.coroutines.cancel
import ru.astrainteractive.astratemplate.modules.SQLDatabaseModule
import ru.astrainteractive.astratemplate.modules.TranslationModule
import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.events.GlobalEventManager
import ru.astrainteractive.astralibs.menu.SharedInventoryClickEvent
import ru.astrainteractive.astratemplate.events.EventHandler
import ru.astrainteractive.astratemplate.modules.PluginConfigModule
import ru.astrainteractive.astratemplate.modules.eventHandlerModule
import ru.astrainteractive.astratemplate.utils.Files
import ru.astrainteractive.astratemplate.utils.Singleton

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
    private val eventHandler: EventHandler by eventHandlerModule



    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        AstraLibs.rememberPlugin(this)
        Logger.prefix = "AstraTemplate"
        eventHandler.onEnable()
        CommandManager()
        SharedInventoryClickEvent.onEnable(GlobalEventManager)
        Logger.log("Logger enabled", "AstraTemplate")
        Logger.warn("Warn message from logger", "AstraTemplate")
        Logger.error("Error message", "AstraTemplate")
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventHandler.onDisable()
        runBlocking { SQLDatabaseModule.value.closeConnection() }
        HandlerList.unregisterAll(this)
        GlobalEventManager.onDisable()
        PluginScope.cancel()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        Files.configFile.reload()
        PluginConfigModule.reload()
        TranslationModule.reload()
    }

}


