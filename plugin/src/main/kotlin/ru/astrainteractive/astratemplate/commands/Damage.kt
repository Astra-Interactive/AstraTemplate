package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astratemplate.commands.Arguments.Companion.getArgumentString
import ru.astrainteractive.astratemplate.modules.TranslationProvider
import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.utils.registerCommand
import ru.astrainteractive.astralibs.utils.registerTabCompleter
import ru.astrainteractive.astratemplate.utils.AstraPermission

/**
 * Damage player command
 */

private class Arguments {
    companion object {
        val playerName: Pair<String, Int>
            get() = "playerName" to 0
        val damage: Pair<String, Int>
            get() = "damage" to 1

        fun get(argument: Pair<String, Int>, args: Array<out String>): String? = args.getOrNull(argument.second)
        fun Array<out String>.getArgumentString(argument: Pair<String, Int>) = getOrNull(argument.second)
    }
}


fun CommandManager.damageCompleter() = AstraLibs.registerTabCompleter("adamage") { sender, args ->
    return@registerTabCompleter when (args.size) {
        0 -> listOf("adamage")
        1 -> Bukkit.getOnlinePlayers().map { it.name }
        2 -> listOf(translation.damageHint)
        else -> Bukkit.getOnlinePlayers().map { it.name }
    }

}

fun CommandManager.damageCommand() = AstraLibs.registerCommand("adamage") { sender, args ->
    if (!AstraPermission.Damage.hasPermission(sender)) {
        sender.sendMessage(translation.noPermission)
        return@registerCommand
    }
    val playerName = args.getArgumentString(Arguments.playerName)
    if (playerName == null) {
        sender.sendMessage(translation.noPlayerName)
        return@registerCommand
    }
    val damage = args.getArgumentString(Arguments.damage)?.toIntOrNull() ?: 1
    val player = Bukkit.getPlayer(playerName)
    if (player == null) {
        sender.sendMessage(translation.noPlayerName)
        return@registerCommand
    }
    player.damage(damage.toDouble())
    player.sendMessage(translation.damaged.replace("%player%", sender.name))
}

