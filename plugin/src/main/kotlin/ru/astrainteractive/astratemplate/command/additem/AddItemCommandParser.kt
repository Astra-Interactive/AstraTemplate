package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.exception.BukkitCommandException
import ru.astrainteractive.astralibs.command.api.parser.CommandParser

internal class AddItemCommandParser : CommandParser<AddItemCommand.Result, BukkitCommandContext> {
    override fun parse(commandContext: BukkitCommandContext): AddItemCommand.Result {
        if (commandContext.sender !is Player) throw AddItemCommand.Error.SenderNotPlayer

        val playerString = commandContext.args.getOrNull(0)
        val player = playerString
            ?.let { value -> Bukkit.getOnlinePlayers().firstOrNull { it.name == value } }
            ?: throw BukkitCommandException.NoPlayerException(playerString.orEmpty())

        val amount = commandContext.args.getOrNull(2)
            ?.toIntOrNull() ?: 1

        val item = commandContext.args.getOrNull(1)
            ?.let(Material::getMaterial)
            ?: throw AddItemCommand.Error.ItemNotfound

        return AddItemCommand.Result(
            player = player,
            amount = amount,
            item = item
        )
    }
}
