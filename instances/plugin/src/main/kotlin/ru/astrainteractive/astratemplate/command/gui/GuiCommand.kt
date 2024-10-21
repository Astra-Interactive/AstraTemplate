package ru.astrainteractive.astratemplate.command.gui

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.exception.CommandException
import ru.astrainteractive.astratemplate.gui.router.Router

internal interface GuiCommand {
    class Result(
        val player: Player,
        val route: Router.Route
    )

    sealed class Error(message: String) : CommandException(message) {
        data object NotPlayer : Error("NotPlayer")
    }
}
