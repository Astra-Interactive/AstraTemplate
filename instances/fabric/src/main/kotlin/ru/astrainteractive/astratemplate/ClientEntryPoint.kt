package ru.astrainteractive.astratemplate

import net.fabricmc.api.ClientModInitializer
import ru.astrainteractive.astratemplate.di.RootModule

class ClientEntryPoint : ClientModInitializer {

    private val rootModule: RootModule by lazy {
        RootModule.Default()
    }

    override fun onInitializeClient() {
        println("AstraTemplate: onInitializeClient")
        rootModule.lifecycle.onEnable()
    }
}
