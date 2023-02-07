package ru.astrainteractive.astratemplate.commands

import CommandManager
import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.commands.registerTabCompleter
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.utils.AstraPermission

/**
 * Damage player command
 */

fun CommandManager.damageCompleter() = AstraTemplate.instance.registerTabCompleter("adamage") {
    return@registerTabCompleter when (args.size) {
        0 -> listOf("adamage")
        1 -> Bukkit.getOnlinePlayers().map { it.name }
        2 -> listOf(translation.damageHint)
        else -> Bukkit.getOnlinePlayers().map { it.name }
    }
}

fun CommandManager.damageCommand() = AstraTemplate.instance.registerCommand("adamage") {
    if (!AstraPermission.Damage.hasPermission(sender)) {
        sender.sendMessage(translation.noPermission)
        return@registerCommand
    }
    val player = argument(0) {
        it?.let(Bukkit::getPlayer)
    }.onFailure { sender.sendMessage(translation.noPlayerName) }.successOrNull()?.value ?: return@registerCommand
    val damage = argument(1) { it?.toIntOrNull() ?: 1 }.successOrNull()?.value!!
    player.damage(damage.toDouble())
    player.sendMessage(translation.damaged.replace("%player%", sender.name))
}
