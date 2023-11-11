package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.CommandParser

internal class AddItemCommandParser : CommandParser<AddItemCommand.Result> {

    override fun parse(
        args: Array<out String>,
        sender: CommandSender
    ): AddItemCommand.Result {
        if (sender !is Player) return AddItemCommand.Result.SenderNotPlayer

        val player = args.getOrNull(0)
            ?.let { value -> Bukkit.getOnlinePlayers().firstOrNull { it.name == value } }
            ?: return AddItemCommand.Result.PlayerNotExists

        val amount = args.getOrNull(2)
            ?.toIntOrNull() ?: 1

        val item = args.getOrNull(1)
            ?.let(Material::getMaterial)
            ?: return AddItemCommand.Result.ItemNotfound

        return AddItemCommand.Result.Success(
            player = player,
            amount = amount,
            item = item
        )
    }
}
