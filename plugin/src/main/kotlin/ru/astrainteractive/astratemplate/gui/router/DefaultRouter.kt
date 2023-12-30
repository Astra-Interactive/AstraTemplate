package ru.astrainteractive.astratemplate.gui.router

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astratemplate.gui.di.SampleGuiDependencies
import ru.astrainteractive.astratemplate.gui.sample.SampleGUI

class DefaultRouter(
    private val dependencies: SampleGuiDependencies,
    private val scope: CoroutineScope,
    private val dispatchers: BukkitDispatchers
) : Router {
    private fun buildRoute(player: Player, route: Router.Route) = when (route) {
        Router.Route.Sample -> SampleGUI(
            player = player,
            dependencies = dependencies
        )
    }

    override fun open(player: Player, route: Router.Route) {
        scope.launch(dispatchers.BukkitAsync) {
            val gui = buildRoute(player, route)
            withContext(dispatchers.BukkitMain) { gui.open() }
        }
    }
}
