package ru.astrainteractive.astratemplate

import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.di.RootModule

/**
 * Initial class for your plugin
 */

class AstraTemplate :
    JavaPlugin(),
    Logger by JUtiltLogger("AstraTemplate"),
    Lifecycle {
    private val rootModule = RootModule(this)

    /**
     * This method called when server starts or PlugMan load plugin.
     */
    override fun onEnable() {
        info { "#onEnable Logger enabled" }
        warn { "#onEnable Warn message from logger" }
        error { "#onEnable Error message" }
        rootModule.lifecycle.onEnable()
    }

    /**
     * This method called when server is shutting down or when PlugMan disable plugin.
     */
    override fun onDisable() {
        rootModule.lifecycle.onDisable()
    }

    /**
     * As it says, function for plugin reload
     */
    override fun onReload() {
        rootModule.lifecycle.onReload()
    }
}
