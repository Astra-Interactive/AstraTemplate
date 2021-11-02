package com.astrainteractive.astratemplate.commands


import com.astrainteractive.astralibs.withEntry
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter


/**
 * Tab completer for your plugin which is called when player typing commands
 */
public class EmpireTabCompleter() : TabCompleter {

    /**
     * Entry point for commands
     */
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): List<String>? {
        if (args.isEmpty())
            return listOf("etemp", "etempreload")
        if (args.size == 1)
            return listOf("etemp", "etempreload").withEntry(args.last())
        return null
    }


}