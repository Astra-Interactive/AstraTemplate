package ru.astrainteractive.astratemplate.commands

import CommandManager
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.commands.registerTabCompleter
import ru.astrainteractive.astralibs.utils.withEntry

/**
 * Tab completer for your plugin which is called when player typing commands
 */
fun CommandManager.tabCompleter(plugin: JavaPlugin) = plugin.registerTabCompleter("atemp") {
    when {
        args.isEmpty() -> listOf("atemp", "atempreload")
        args.size == 1 -> listOf("atemp", "atempreload").withEntry(args.last())
        else -> emptyList()
    }
}
