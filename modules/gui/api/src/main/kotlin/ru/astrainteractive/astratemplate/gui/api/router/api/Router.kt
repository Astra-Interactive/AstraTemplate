package ru.astrainteractive.astratemplate.gui.api.router.api

import ru.astrainteractive.astralibs.server.player.OnlineKPlayer

interface Router {
    sealed interface Route {
        data object Sample : Route
    }

    fun open(player: OnlineKPlayer, route: Route)
}
