package com.astrainteractive.astratemplate

import CommandManager
import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.Logger
import com.astrainteractive.astralibs.ServerVersion
import com.astrainteractive.astralibs.events.GlobalEventManager
import com.astrainteractive.astralibs.rest.RestRequester
import com.astrainteractive.astralibs.utils.Injector.inject
import com.astrainteractive.astralibs.utils.Injector.remember
import com.astrainteractive.astratemplate.api.Repository
import com.astrainteractive.astratemplate.api.TemplateApi
import com.astrainteractive.astratemplate.events.EventHandler
import com.astrainteractive.astratemplate.api.remote.RestApi
import com.astrainteractive.astratemplate.api.local.SQLDatabase
import com.astrainteractive.astratemplate.api.remote.RestBuilder
import com.astrainteractive.astratemplate.utils.PluginTranslation
import com.astrainteractive.astratemplate.utils._Files
import com.astrainteractive.astratemplate.utils._EmpireConfig
import kotlinx.coroutines.runBlocking
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.kotlin.com.google.gson.Gson

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
        PluginTranslation()
        _Files()

        remember(runBlocking { SQLDatabase().apply { onEnable() } })
        remember(Repository(restDataSource = RestBuilder.build()))


        eventHandler = EventHandler()
        commandManager = CommandManager()
        _EmpireConfig.create()
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
        TemplateApi.onEnable()
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventHandler.onDisable()
        runBlocking { inject<SQLDatabase>()?.close() }
        HandlerList.unregisterAll(this)
        TemplateApi.onDisable()
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


