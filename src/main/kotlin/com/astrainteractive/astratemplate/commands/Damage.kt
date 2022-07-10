package com.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerCommand
import com.astrainteractive.astralibs.registerTabCompleter
import com.astrainteractive.astratemplate.commands.Arguments.Companion.getArgumentString
import com.astrainteractive.astratemplate.utils.EmpirePermissions
import com.astrainteractive.astratemplate.utils.Translation
import org.bukkit.Bukkit

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
        2 -> listOf(Translation.damageHint)
        else -> Bukkit.getOnlinePlayers().map { it.name }
    }

}

fun CommandManager.damageCommand() = AstraLibs.registerCommand("adamage") { sender, args ->
    if (!sender.hasPermission(EmpirePermissions.damage)) {
        sender.sendMessage(Translation.noPermission)
        return@registerCommand
    }
    val playerName = args.getArgumentString(Arguments.playerName)
    if (playerName == null) {
        sender.sendMessage(Translation.noPlayerName)
        return@registerCommand
    }
    val damage = args.getArgumentString(Arguments.damage)?.toIntOrNull() ?: 1
    val player = Bukkit.getPlayer(playerName)
    if (player == null) {
        sender.sendMessage(Translation.noPlayerName)
        return@registerCommand
    }
    player.damage(damage.toDouble())
    player.sendMessage(Translation.damaged.replace("%player%", sender.name))
}

