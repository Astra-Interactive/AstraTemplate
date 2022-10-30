package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astratemplate.gui.SampleGUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.utils.registerCommand

fun CommandManager.tempGUI() = AstraLibs.registerCommand("atempgui") { sender, args ->
    if (sender is Player)
        PluginScope.launch(Dispatchers.IO) { SampleGUI(sender).open() }
}
