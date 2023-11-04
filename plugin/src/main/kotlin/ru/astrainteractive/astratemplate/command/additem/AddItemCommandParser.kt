package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.CommandParser

internal class AddItemCommandParser(
    override val alias: String
) : CommandParser<AddItemCommandParser.Result> {

    sealed interface Result {
        data object NoEx : Result
        data object SenderNotPlayer : Result
        data object PlayerNotExists : Result
        data object ItemNotfound : Result
        class Success(
            val player: Player,
            val amount: Int,
            val item: Material
        ) : Result
    }

    override fun parse(
        args: Array<out String>,
        sender: CommandSender
    ): Result {
        if (sender !is Player) return Result.SenderNotPlayer

        val player = args.getOrNull(0)
            ?.let { value -> Bukkit.getOnlinePlayers().firstOrNull { it.name == value } }
            ?: return Result.PlayerNotExists

        val amount = args.getOrNull(2)
            ?.toIntOrNull() ?: 1

        val item = args.getOrNull(1)
            ?.let(Material::getMaterial)
            ?: return Result.ItemNotfound

        return Result.Success(
            player = player,
            amount = amount,
            item = item
        )
    }
}
