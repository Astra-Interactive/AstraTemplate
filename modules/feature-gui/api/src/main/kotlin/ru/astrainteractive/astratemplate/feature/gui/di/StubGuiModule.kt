package ru.astrainteractive.astratemplate.feature.gui.di

import ru.astrainteractive.astralibs.server.player.OnlineKPlayer
import ru.astrainteractive.astratemplate.feature.gui.router.Router

class StubGuiModule : GuiModule {
    override val router: Router = object : Router {
        override fun open(
            player: OnlineKPlayer,
            route: Router.Route
        ) = Unit
    }
}
