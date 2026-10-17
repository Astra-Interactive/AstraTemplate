package ru.astrainteractive.astratemplate.gui.bukkit.router

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.inventory.Inventory
import ru.astrainteractive.astralibs.server.player.BukkitOnlineKPlayer
import ru.astrainteractive.astralibs.server.player.OnlineKPlayer
import ru.astrainteractive.astratemplate.data.dao.user.api.UserRepository
import ru.astrainteractive.astratemplate.gui.api.router.api.Router
import ru.astrainteractive.astratemplate.gui.bukkit.context.SlotContext
import ru.astrainteractive.astratemplate.gui.bukkit.sample.component.DefaultSampleGUIComponent
import ru.astrainteractive.astratemplate.gui.bukkit.sample.gui.SampleGUI
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import ru.astrainteractive.klibs.mikro.core.util.tryCast
import java.util.Locale
import kotlin.random.Random

internal class BukkitRouter(
    private val ioScope: CoroutineScope,
    private val dispatchers: KotlinDispatchers,
    private val createSlotContext: (Locale) -> SlotContext,
    private val userRepository: UserRepository,
    private val random: Random
) : Router, Logger by JUtiltLogger("Router") {
    private fun buildRoute(route: Router.Route, locale: Locale): Inventory {
        return when (route) {
            Router.Route.Sample -> SampleGUI(
                slotContext = createSlotContext(locale),
                dispatchers = dispatchers,
                sampleComponent = DefaultSampleGUIComponent(
                    userRepository = userRepository,
                    random = random
                )
            ).inventory
        }
    }

    override fun open(player: OnlineKPlayer, route: Router.Route) {
        ioScope.launch {
            val bukkitPlayer = player.tryCast<BukkitOnlineKPlayer>()?.instance
            if (bukkitPlayer == null) {
                error { "#open Could not cast OnlineKPlayer to BukkitOnlineKPlayer" }
                return@launch
            }
            val inventory = buildRoute(route, player.locale)
            withContext(dispatchers.Main) { bukkitPlayer.openInventory(inventory) }
        }
    }
}
