package ru.astrainteractive.astratemplate.commands

import CommandManager
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.commands.registerTabCompleter
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule
import ru.astrainteractive.astratemplate.plugin.Permissions

/**
 * Damage player command
 */

fun CommandManager.damageCompleter(
    plugin: JavaPlugin,
    module: CommandManagerModule
) = plugin.registerTabCompleter("adamage") {
    val translation by module.translationModule
    when (args.size) {
        0 -> listOf("adamage")
        1 -> Bukkit.getOnlinePlayers().map { it.name }
        2 -> listOf(translation.damageHint)
        else -> Bukkit.getOnlinePlayers().map { it.name }
    }
}

fun CommandManager.damageCommand(
    plugin: JavaPlugin,
    module: CommandManagerModule
) = plugin.registerCommand("adamage") {
    val translation by module.translationModule

    if (!Permissions.Damage.hasPermission(sender)) {
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
