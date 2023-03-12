package ru.astrainteractive.astratemplate

import net.fabricmc.api.ClientModInitializer

class ClientEntryPoint : ClientModInitializer {
    override fun onInitializeClient() {
        println("AstraTemplate: onInitializeClient")
    }
}
