package com.astrainteractive.astratemplate.commands


import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerTabCompleter
import com.astrainteractive.astralibs.withEntry
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter


/**
 * Tab completer for your plugin which is called when player typing commands
 */
public class EmpireTabCompleter {
    val tabCompleter = AstraLibs.registerTabCompleter("atemp") { sender, args ->
        if (args.isEmpty())
            return@registerTabCompleter listOf("atemp", "atempreload")
        if (args.size == 1)
            return@registerTabCompleter listOf("atemp", "atempreload").withEntry(args.last())
        return@registerTabCompleter listOf<String>()
    }
}