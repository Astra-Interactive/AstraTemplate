package ru.astrainteractive.astratemplate.commands

import CommandManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.modules.ServiceLocator

fun CommandManager.tempGUI(
    guisFactories: ServiceLocator.Guis
) = AstraTemplate.instance.registerCommand("atempgui") {
    val player = sender as? Player ?: return@registerCommand

    PluginScope.launch(Dispatchers.IO) {
        val sampleGUI = guisFactories.sampleGuiFactory(player = player).value
        sampleGUI.open()
    }
}
