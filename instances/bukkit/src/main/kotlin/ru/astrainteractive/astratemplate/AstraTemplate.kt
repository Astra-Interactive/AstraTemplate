package ru.astrainteractive.astratemplate

import ru.astrainteractive.astralibs.lifecycle.LifecyclePlugin
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

/**
 * Initial class for your plugin
 */

class AstraTemplate :
    LifecyclePlugin(),
    Logger by JUtiltLogger("AstraTemplate") {
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
