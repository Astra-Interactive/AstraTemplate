package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astralibs.commands.registerTabCompleter
import ru.astrainteractive.astralibs.utils.withEntry
import ru.astrainteractive.astratemplate.AstraTemplate

/**
 * Tab completer for your plugin which is called when player typing commands
 */
fun CommandManager.tabCompleter() = AstraTemplate.instance.registerTabCompleter("atemp") {
    when {
        args.isEmpty() -> listOf("atemp", "atempreload")
        args.size == 1 -> listOf("atemp", "atempreload").withEntry(args.last())
        else -> emptyList()
    }
}
