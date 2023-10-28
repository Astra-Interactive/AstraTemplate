package ru.astrainteractive.astratemplate.command

import CommandManager
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.command.types.OnlinePlayerArgument
import ru.astrainteractive.astratemplate.shared.core.Permissions

/**
 * Damage player command
 */

fun CommandManager.damageCompleter() = plugin.registerTabCompleter("adamage") {
    when (args.size) {
        0 -> listOf("adamage")
        1 -> Bukkit.getOnlinePlayers().map { it.name }
        2 -> listOf(translation.custom.damageHint.raw)
        else -> Bukkit.getOnlinePlayers().map { it.name }
    }
}

fun CommandManager.damageCommand() = plugin.registerCommand("adamage") {
    (sender as? Player)?.let {
        if (!permissionManager.hasPermission(it.uniqueId, Permissions.Damage)) {
            sender.sendMessage(translation.general.noPermission)
            return@registerCommand
        }
    }
    val player = argument(0, OnlinePlayerArgument)
        .onFailure { sender.sendMessage(translation.custom.noPlayerName) }
        .resultOrNull() ?: return@registerCommand
    val damage = argument(1) { it.toIntOrNull() ?: 1 }
        .resultOrNull() ?: return@registerCommand
    player.damage(damage.toDouble())
    player.sendMessage(translation.custom.damaged)
}
