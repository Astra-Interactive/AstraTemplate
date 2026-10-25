package ru.astrainteractive.astratemplate.gui.api.di

import ru.astrainteractive.astralibs.server.player.OnlineKPlayer
import ru.astrainteractive.astratemplate.gui.api.router.api.Router

class StubGuiModule : GuiModule {
    override val router: Router = object : Router {
        override fun open(
            player: OnlineKPlayer,
            route: Router.Route
        ) {
            Unit
        }
    }
}
