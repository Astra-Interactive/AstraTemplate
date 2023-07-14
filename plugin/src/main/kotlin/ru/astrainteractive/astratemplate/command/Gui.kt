package ru.astrainteractive.astratemplate.command

import CommandManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.commands.registerCommand

fun CommandManager.tempGUI() = plugin.registerCommand("atempgui") {
    val player = sender as? Player ?: return@registerCommand
    pluginScope.launch(dispatchers.BukkitAsync) {
        val sampleGUI = sampleGuiFactory(player = player).create()
        withContext(dispatchers.BukkitMain) { sampleGUI.open() }
    }
}
