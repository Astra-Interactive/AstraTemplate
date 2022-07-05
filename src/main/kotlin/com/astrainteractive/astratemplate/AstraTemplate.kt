package com.astrainteractive.astratemplate

import CommandManager
import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astralibs.ServerVersion
import com.astrainteractive.astralibs.events.GlobalEventManager
import com.astrainteractive.astratemplate.api.Api
import com.astrainteractive.astratemplate.events.EventHandler
import com.astrainteractive.astratemplate.sqldatabase.Database
import com.astrainteractive.astratemplate.utils.PluginTranslation
import com.astrainteractive.astratemplate.utils.Files
import com.astrainteractive.astratemplate.utils.config.EmpireConfig
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin

/**
 * Initial class for your plugin
 */
class AstraTemplate : JavaPlugin() {

    /**
     * Static objects of this class
     */
    companion object {
        lateinit var instance: AstraTemplate
            private set
        lateinit var translations: PluginTranslation
            private set
        lateinit var empireFiles: Files
            private set
        lateinit var pluginConfig: EmpireConfig
            private set
        lateinit var database: Database
            private set
    }

    /**
     * Class for handling all of your events
     */
    private lateinit var eventHandler: EventHandler

    /**
     * Command manager for your commands.
     *
     * You can create multiple managers.
     */
    private lateinit var commandManager: CommandManager


    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        AstraLibs.rememberPlugin(this)
        Logger.prefix = "AstraTemplate"
        instance = this
        translations = PluginTranslation()
        empireFiles = Files()
        eventHandler = EventHandler()
        commandManager = CommandManager()
        pluginConfig = EmpireConfig.new2()
        Logger.log("$pluginConfig", tag = "EmpireConfig")
//        database = Database().apply { onEnable() }
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

        Api.onEnable()
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventHandler.onDisable()
//        database.onDisable()
//        HandlerList.unregisterAll(this)
        Api.onDisable()
        GlobalEventManager.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        onDisable()
        onEnable()
    }

}


