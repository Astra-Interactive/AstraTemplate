package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.util.PluginExt.setCommandExecutor
import ru.astrainteractive.astralibs.util.StringListExt.withEntry
import ru.astrainteractive.astratemplate.command.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.additem.di.AddItemCommandDependencies

internal class AddItemCommandRegistry(
    dependencies: AddItemCommandDependencies
) : AddItemCommandDependencies by dependencies {
    private val alias = "add"

    private fun tabCompleter(plugin: JavaPlugin) {
        plugin.getCommand(alias)?.setTabCompleter { sender, command, label, args ->
            when (args.size) {
                2 -> Material.entries.map { it.name }.withEntry(args.last())
                3 -> IntRange(1, 64).map { it.toString() }.withEntry(args.last())
                else -> Bukkit.getOnlinePlayers().map { it.name }.withEntry(args.last())
            }
        }
    }

    fun register() {
        tabCompleter(plugin)
        plugin.setCommandExecutor(
            alias = alias,
            commandParser = AddItemCommandParser(),
            commandExecutor = AddItemExecutor(),
            errorHandler = DefaultErrorHandler(
                translationKrate = translationKrate,
                kyoriKrate = kyoriKrate
            )
        )
    }
}
