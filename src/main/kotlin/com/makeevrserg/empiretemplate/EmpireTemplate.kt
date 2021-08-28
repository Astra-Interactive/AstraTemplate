package com.makeevrserg.empiretemplate

import CommandManager
import com.makeevrserg.empiretemplate.events.EventHandler
import com.makeevrserg.empiretemplate.utils.Files
import com.makeevrserg.empiretemplate.utils.EmpireTranslation
import com.makeevrserg.empiretemplate.utils.config.EmpireConfig
import org.bukkit.plugin.java.JavaPlugin

/**
 * Initial class for your plugin
 */
class EmpireTemplate : JavaPlugin() {

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
        lateinit var instance: EmpireTemplate
            private set
        lateinit var translations: EmpireTranslation
            private set
        lateinit var empireFiles: Files
            private set

        lateinit var pluginConfig: EmpireConfig
            private set
//        public lateinit var database: EmpireDatabase
//            private set
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
        instance = this
        translations = EmpireTranslation()
        empireFiles = Files()
        eventHandler = EventHandler()
        commandManager = CommandManager()
        pluginConfig = EmpireConfig.new1()
        EmpireConfig.new2()
        EmpireConfig.new3()
//        database = EmpireDatabase()
    }

    /**
     * This method called when server is shutting down.
     *
     * Or when PlugMan disable plugin.
     */
    override fun onDisable() {
        eventHandler.onDisable()

//        database.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    fun reloadPlugin() {
        onDisable()
        onEnable()

    }
}