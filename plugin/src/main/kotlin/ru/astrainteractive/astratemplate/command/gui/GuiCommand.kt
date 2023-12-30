package ru.astrainteractive.astratemplate.command.gui

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astratemplate.command.gui.GuiCommand.Output
import ru.astrainteractive.astratemplate.gui.router.Router

interface GuiCommand : Command<Output, Output> {
    sealed interface Output {
        data object NotPlayer : Output
        class Route(val player: Player, val route: Router.Route) : Output
    }
}
