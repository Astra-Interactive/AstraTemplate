package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.command.Command
import ru.astrainteractive.astralibs.command.api.commandfactory.BukkitCommandFactory
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistry
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistryContext.Companion.toCommandRegistryContext
import ru.astrainteractive.astralibs.util.StringListExt.withEntry
import ru.astrainteractive.astratemplate.command.additem.di.AddItemCommandDependencies

class AddItemCommandRegistry(
    dependencies: AddItemCommandDependencies
) : AddItemCommandDependencies by dependencies {
    private val alias = "add"

    internal class Mapper : Command.Mapper<AddItemCommand.Result, AddItemCommand.Input> {
        override fun toInput(result: AddItemCommand.Result): AddItemCommand.Input? {
            return (result as? AddItemCommand.Result.Success)?.let {
                AddItemCommand.Input(
                    player = it.player,
                    amount = it.amount,
                    item = it.item
                )
            }
        }
    }

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
        val command = BukkitCommandFactory.create(
            alias = alias,
            commandParser = AddItemCommandParser(),
            commandExecutor = AddItemExecutor(),
            commandSideEffect = AddItemParserResultHandler(),
            mapper = Mapper()
        )
        BukkitCommandRegistry.register(
            command = command,
            registryContext = plugin.toCommandRegistryContext()
        )
    }
}
