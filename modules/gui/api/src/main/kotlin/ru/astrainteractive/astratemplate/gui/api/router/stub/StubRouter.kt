package ru.astrainteractive.astratemplate.gui.api.router.stub

import ru.astrainteractive.astralibs.server.player.OnlineKPlayer
import ru.astrainteractive.astratemplate.gui.api.router.api.Router

internal class StubRouter : Router {
    override fun open(
        player: OnlineKPlayer,
        route: Router.Route
    ) {
        Unit
    }
}
