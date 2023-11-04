package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.util.withEntry

class AddItemCommand : Command {
    private val commandParser = AddItemCommandParser("add")
    private val executor = AddItemExecutor()
    private val parseResultHandler = AddItemParserResultHandler()

    private fun toInput(result: AddItemCommandParser.Result): AddItemExecutor.Input? {
        return (result as? AddItemCommandParser.Result.Success)?.let {
            AddItemExecutor.Input(
                player = it.player,
                amount = it.amount,
                item = it.item
            )
        }
    }

    override fun register(plugin: JavaPlugin) {
        tabCompleter(plugin)
        Command.registerDefault(
            plugin = plugin,
            commandParser = commandParser,
            commandExecutor = executor,
            resultHandler = parseResultHandler,
            transform = ::toInput
        )
    }

    private fun tabCompleter(plugin: JavaPlugin) = plugin.registerTabCompleter(commandParser.alias) {
        when (args.size) {
            2 -> Material.entries.map { it.name }.withEntry(args.last())
            3 -> IntRange(1, 64).map { it.toString() }.withEntry(args.last())
            else -> Bukkit.getOnlinePlayers().map { it.name }.withEntry(args.last())
        }
    }
}
