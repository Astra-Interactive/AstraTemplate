package com.makeevrserg.empiretemplate

import CommandManager
import com.makeevrserg.empiretemplate.database.EmpireDatabase
import com.makeevrserg.empiretemplate.events.EventHandler
import com.makeevrserg.empiretemplate.utils.Files
import com.makeevrserg.empiretemplate.utils.EmpireTranslation
import org.bukkit.plugin.java.JavaPlugin

/**
 * Initial class for your plugin
 */
public final class EmpireTemplate : JavaPlugin() {

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
        public lateinit var instance: EmpireTemplate
            private set
        public lateinit var translation: EmpireTranslation
            private set
        public lateinit var empireFiles: Files
            private set
        public lateinit var database: EmpireDatabase
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
     * Made for PlugMan support, so you don't need to reload server for plugin updating
     */
    public fun loadPlugin() {
        translation = EmpireTranslation()
        empireFiles = Files()
        eventHandler = EventHandler()
        commandManager = CommandManager()
        database = EmpireDatabase()

    }


    /**
     * Made for PlugMan support, so you don't need to reload server for plugin updating
     */
    public fun disablePlugin() {
        eventHandler.onDisable()
        database.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    public fun reloadPlugin(){
        disablePlugin()
        loadPlugin()
    }

    /**
     * This method called when server starts.
     *
     * When server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        instance = this
        loadPlugin()
    }

    /**
     * This method called when server is shutting down.
     *
     * Or when PlugMan disable plugin.
     */
    override fun onDisable() {
        disablePlugin()
    }
}