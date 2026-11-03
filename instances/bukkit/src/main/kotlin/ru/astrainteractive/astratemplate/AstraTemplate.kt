@file:Suppress("FieldDependencyConstruction")

package ru.astrainteractive.astratemplate

import ru.astrainteractive.astralibs.lifecycle.LifecyclePlugin
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

class AstraTemplate :
    LifecyclePlugin(),
    Logger by JUtiltLogger("AstraTemplate") {
    private val rootModule = RootModule(this)

    override fun onEnable() {
        info { "#onEnable Logger enabled" }
        warn { "#onEnable Warn message from logger" }
        error { "#onEnable Error message" }
        rootModule.lifecycle.onEnable()
    }

    override fun onDisable() {
        rootModule.lifecycle.onDisable()
    }

    override fun onReload() {
        rootModule.lifecycle.onReload()
    }
}
