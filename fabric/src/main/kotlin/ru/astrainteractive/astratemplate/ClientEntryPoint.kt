package ru.astrainteractive.astratemplate

import net.fabricmc.api.ClientModInitializer
import ru.astrainteractive.astralibs.di.module

val HelloWorldModule = module {
    "Hello world"
}
class ClientEntryPoint : ClientModInitializer {
    override fun onInitializeClient() {
        println("AstraTemplate: onInitializeClient")
    }
}
