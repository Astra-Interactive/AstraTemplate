package com.makeevrserg.empiretemplate

import CommandManager
import com.makeevrserg.empireprojekt.util.files.Files
import com.makeevrserg.empiretemplate.database.EmpireDatabase
import com.makeevrserg.empiretemplate.events.EventHandler
import com.makeevrserg.empiretemplate.menumanager.MenuConfig
import com.makeevrserg.empiretemplate.utils.Translations
import org.bukkit.plugin.java.JavaPlugin

public final class EmpireTemplate : JavaPlugin() {


    lateinit var empireFiles: Files
    lateinit var empireTranslations: Translations
    lateinit var eventHandler: EventHandler
    private lateinit var commandManager: CommandManager
    public lateinit var empireMenuConfig:MenuConfig
    private lateinit var database:EmpireDatabase
    public fun initPlugin() {
        empireFiles = Files()
        empireTranslations = Translations()
        eventHandler = EventHandler()
        commandManager = CommandManager()
        empireMenuConfig = MenuConfig()

        database = EmpireDatabase()

    }

    public fun disablePlugin() {
        eventHandler.onDisable()
        empireMenuConfig.onDisable()
        database.onDisable()
    }



    override fun onEnable() {
        plugin = this
        initPlugin()
    }

    companion object {
        public lateinit var plugin: EmpireTemplate
            private set
    }

    override fun onDisable() {
        disablePlugin()
    }
}