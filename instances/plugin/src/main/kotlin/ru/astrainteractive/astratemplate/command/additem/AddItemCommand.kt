package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Material
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.exception.CommandException

internal interface AddItemCommand {
    class Result(
        val player: Player,
        val amount: Int,
        val item: Material
    )

    sealed class Error(message: String) : CommandException(message) {
        data object SenderNotPlayer : Error("SenderNotPlayer")
        data object ItemNotfound : Error("ItemNotfound")
    }
}
