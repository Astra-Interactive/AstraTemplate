package ru.astrainteractive.astratemplate.command

import CommandManager
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astratemplate.gui.router.Router

fun CommandManager.tempGUI() = plugin.registerCommand("atempgui") {
    val player = sender as? Player ?: return@registerCommand
    val route = Router.Route.Sample
    router.open(player, route)
}
