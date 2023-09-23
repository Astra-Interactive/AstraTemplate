package ru.astrainteractive.astratemplate.command

import CommandManager
import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astratemplate.plugin.Permissions

/**
 * Damage player command
 */

fun CommandManager.damageCompleter() = plugin.registerTabCompleter("adamage") {
    when (args.size) {
        0 -> listOf("adamage")
        1 -> Bukkit.getOnlinePlayers().map { it.name }
        2 -> listOf(translation.custom.damageHint)
        else -> Bukkit.getOnlinePlayers().map { it.name }
    }
}

fun CommandManager.damageCommand() = plugin.registerCommand("adamage") {
    if (!Permissions.Damage.hasPermission(sender)) {
        sender.sendMessage(translation.general.noPermission)
        return@registerCommand
    }
    val player = argument(0) {
        it?.let(Bukkit::getPlayer)
    }.onFailure { sender.sendMessage(translation.custom.noPlayerName) }.successOrNull()?.value ?: return@registerCommand
    val damage = argument(1) { it?.toIntOrNull() ?: 1 }.successOrNull()?.value!!
    player.damage(damage.toDouble())
    player.sendMessage(translation.custom.damaged.replace("%player%", sender.name))
}
