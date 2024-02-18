package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.parser.BukkitCommandParser

internal class AddItemCommandParser : BukkitCommandParser<AddItemCommand.Result> {
    override fun parse(commandContext: BukkitCommandContext): AddItemCommand.Result {
        if (commandContext.sender !is Player) return AddItemCommand.Result.SenderNotPlayer

        val player = commandContext.args.getOrNull(0)
            ?.let { value -> Bukkit.getOnlinePlayers().firstOrNull { it.name == value } }
            ?: return AddItemCommand.Result.PlayerNotExists

        val amount = commandContext.args.getOrNull(2)
            ?.toIntOrNull() ?: 1

        val item = commandContext.args.getOrNull(1)
            ?.let(Material::getMaterial)
            ?: return AddItemCommand.Result.ItemNotfound

        return AddItemCommand.Result.Success(
            player = player,
            amount = amount,
            item = item
        )
    }
}
