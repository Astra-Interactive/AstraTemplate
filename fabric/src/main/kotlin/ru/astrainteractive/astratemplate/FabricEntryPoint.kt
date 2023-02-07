package ru.astrainteractive.astratemplate

import net.fabricmc.api.ModInitializer
import ru.astrainteractive.astratemplate.command.CommandManager

class FabricEntryPoint : ModInitializer {

    override fun onInitialize() {
        println("AstraTemplate: onInitialize")
        CommandManager.enable()
    }
}
