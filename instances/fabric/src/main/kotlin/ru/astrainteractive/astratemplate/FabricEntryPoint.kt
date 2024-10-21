package ru.astrainteractive.astratemplate

import net.fabricmc.api.ModInitializer
import ru.astrainteractive.astratemplate.di.RootModule

class FabricEntryPoint : ModInitializer {

    private val rootModule: RootModule by lazy {
        RootModule.Default()
    }

    override fun onInitialize() {
        println("AstraTemplate: onInitialize")
        rootModule.lifecycle.onEnable()
    }

    fun reload() {
        rootModule.lifecycle.onReload()
    }
}
