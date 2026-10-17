package ru.astrainteractive.astratemplate.command.additem.model

import ru.astrainteractive.astralibs.server.player.OnlineKPlayer

internal data class AddItemCommand(
    val player: OnlineKPlayer,
    val amount: Int,
    val itemName: String
)
