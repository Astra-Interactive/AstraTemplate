package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.exception.NoPlayerException
import ru.astrainteractive.astralibs.command.api.parser.CommandParser

internal class AddItemCommandParser : CommandParser<AddItemCommand.Result, BukkitCommandContext> {
    override fun parse(ctx: BukkitCommandContext): AddItemCommand.Result {
        if (ctx.sender !is Player) throw AddItemCommand.Error.SenderNotPlayer

        val playerString = ctx.args.getOrNull(0)
        val player = playerString
            ?.let { value -> Bukkit.getOnlinePlayers().firstOrNull { it.name == value } }
            ?: throw NoPlayerException(playerString.orEmpty())

        val amount = ctx.args.getOrNull(2)
            ?.toIntOrNull() ?: 1

        val item = ctx.args.getOrNull(1)
            ?.let(Material::getMaterial)
            ?: throw AddItemCommand.Error.ItemNotfound

        return AddItemCommand.Result(
            player = player,
            amount = amount,
            item = item
        )
    }
}
