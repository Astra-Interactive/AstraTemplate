package com.astrainteractive.astratemplate

import CommandManager
import com.astrainteractive.astratemplate.events.EventHandler
import com.astrainteractive.astratemplate.modules.SQLDatabaseModule
import com.astrainteractive.astratemplate.modules.TranslationProvider
import com.astrainteractive.astratemplate.utils.Files
import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.Logger
import ru.astrainteractive.astralibs.ServerVersion
import ru.astrainteractive.astralibs.events.GlobalEventManager

/**
 * Initial class for your plugin
 */
class AstraTemplate : JavaPlugin() {
    companion object {
        lateinit var instance: AstraTemplate
    }

    init {
        instance = this
    }

    /**
     * Class for handling all of your events
     */
    private var eventHandler: EventHandler? = null


    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        AstraLibs.rememberPlugin(this)
        Logger.prefix = "AstraTemplate"
        eventHandler = EventHandler()
        CommandManager()
        Logger.log("Logger enabled", "AstraTemplate")
        Logger.warn("Warn message from logger", "AstraTemplate")
        Logger.error("Error message", "AstraTemplate")
        if (ServerVersion.version == ServerVersion.UNMAINTAINED)
            Logger.warn("Your server version is not maintained and might be not fully functional!", "AstraTemplate")
        else
            Logger.log(
                "Your server version is: ${ServerVersion.getServerVersion()}. This version is supported!",
                "AstraTemplate"
            )
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventHandler?.onDisable()
        runBlocking { SQLDatabaseModule.value.close() }
        HandlerList.unregisterAll(this)
        GlobalEventManager.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        TranslationProvider.reload()
        Files.configFile.reload()
    }

}


