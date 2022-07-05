package com.astrainteractive.astratemplate.commands


import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerTabCompleter
import com.astrainteractive.astralibs.withEntry
import com.astrainteractive.astratemplate.commands.AstraDSLCommand.onTabComplete
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract


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

