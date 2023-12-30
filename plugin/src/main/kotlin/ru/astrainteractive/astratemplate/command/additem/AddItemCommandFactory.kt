package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.DefaultCommandFactory
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.util.withEntry
import ru.astrainteractive.astratemplate.command.additem.di.AddItemCommandDependencies
import ru.astrainteractive.klibs.kdi.Factory

class AddItemCommandFactory(
    dependencies: AddItemCommandDependencies
) : Factory<AddItemCommand>, AddItemCommandDependencies by dependencies {
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

    inner class AddItemCommandImpl :
        AddItemCommand,
        Command<AddItemCommand.Result, AddItemCommand.Input> by DefaultCommandFactory.create(
            alias = alias,
            commandParser = AddItemCommandParser(),
            commandExecutor = AddItemExecutor(),
            resultHandler = AddItemParserResultHandler(),
            mapper = Mapper()
        )

    private fun tabCompleter(plugin: JavaPlugin) = plugin.registerTabCompleter(alias) {
        when (args.size) {
            2 -> Material.entries.map { it.name }.withEntry(args.last())
            3 -> IntRange(1, 64).map { it.toString() }.withEntry(args.last())
            else -> Bukkit.getOnlinePlayers().map { it.name }.withEntry(args.last())
        }
    }

    override fun create(): AddItemCommand {
        tabCompleter(plugin)
        return AddItemCommandImpl().also {
            it.register(plugin)
        }
    }
}
