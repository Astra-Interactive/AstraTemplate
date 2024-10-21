package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Material
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.exception.CommandException

interface AddItemCommand {
    class Result(
        val player: Player,
        val amount: Int,
        val item: Material
    )

    sealed class Error(message: String) : CommandException(message) {
        data object NoEx : Error("No ex")
        data object SenderNotPlayer : Error("SenderNotPlayer")
        data object PlayerNotExists : Error("PlayerNotExists")
        data object ItemNotfound : Error("ItemNotfound")
    }
}
