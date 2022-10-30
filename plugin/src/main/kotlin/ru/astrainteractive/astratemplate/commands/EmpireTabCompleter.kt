package ru.astrainteractive.astratemplate.commands


import CommandManager
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.utils.registerTabCompleter
import ru.astrainteractive.astralibs.utils.withEntry

/**
 * Tab completer for your plugin which is called when player typing commands
 */
fun CommandManager.tabCompleter() = AstraLibs.registerTabCompleter("atemp") { sender, args ->
    if (args.isEmpty())
        return@registerTabCompleter listOf("atemp", "atempreload")
    if (args.size == 1)
        return@registerTabCompleter listOf("atemp", "atempreload").withEntry(args.last())
    return@registerTabCompleter listOf<String>()
}



