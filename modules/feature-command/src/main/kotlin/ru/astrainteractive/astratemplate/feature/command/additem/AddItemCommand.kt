package ru.astrainteractive.astratemplate.feature.command.additem

import ru.astrainteractive.astralibs.command.api.exception.CommandException
import ru.astrainteractive.astralibs.server.player.OnlineKPlayer

internal interface AddItemCommand {
    class Result(
        val player: OnlineKPlayer,
        val amount: Int,
        val itemName: String
    )

    sealed class Error(message: String) : CommandException(message) {
        class SenderNotPlayer : Error("SenderNotPlayer")
        class ItemNotfound : Error("ItemNotfound")
    }
}
