package com.astrainteractive.astratemplate

import CommandManager
import com.astrainteractive.astralibs.AstraLibs
//import com.makeevrserg.empiretemplate.database.EmpireDatabase
import com.astrainteractive.astratemplate.events.EventHandler
import com.astrainteractive.astratemplate.sqldatabase.Database
import com.astrainteractive.astratemplate.utils.Files
import com.astrainteractive.astratemplate.utils.EmpireTranslation
import com.astrainteractive.astratemplate.utils.config.Config
import com.astrainteractive.astratemplate.utils.config.EmpireConfig
import org.bukkit.plugin.java.JavaPlugin

/**
 * Initial class for your plugin
 */
class AstraTemplate : JavaPlugin() {

    /**
     * Contains instance of the plugin and translation.
     *
     * Translation should be initialized before other classes!
     *
     * Other files of plugin should be initialized after Translation class
     *
     * Instance should be initialized only once
     * @see EmpireTranslation
     */
    companion object {
        lateinit var instance: AstraTemplate
            private set
        lateinit var translations: EmpireTranslation
            private set
        lateinit var empireFiles: Files
            private set

        lateinit var pluginConfig: EmpireConfig
            private set
        public lateinit var database: Database
            private set
    }

    /**
     * Class for handling all of your events
     *
     * Should be private
     */
    private lateinit var eventHandler: EventHandler

    /**
     * Command manager for your commands.
     *
     * You can create multiple managers.
     *
     * Should be private
     */
    private lateinit var commandManager: CommandManager



    /**
     * This method called when server starts.
     *
     * When server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        AstraLibs.create(this)
        instance = this
        translations = EmpireTranslation()
        empireFiles = Files()
        eventHandler = EventHandler()
        commandManager = CommandManager()
        pluginConfig = EmpireConfig.new1()
        database = Database()
        EmpireConfig.new2()
        EmpireConfig.new3()
        Config.print()
        Config.load()
        Config.print()
    }

    /**
     * This method called when server is shutting down.
     *
     * Or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventHandler.onDisable()
        database.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        onDisable()
        onEnable()
    }
}