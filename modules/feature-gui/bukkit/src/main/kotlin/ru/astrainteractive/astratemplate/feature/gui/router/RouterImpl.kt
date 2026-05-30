package ru.astrainteractive.astratemplate.feature.gui.router

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.server.player.BukkitOnlineKPlayer
import ru.astrainteractive.astralibs.server.player.OnlineKPlayer
import ru.astrainteractive.astratemplate.api.local.dao.LocalDao
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.feature.gui.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.feature.gui.domain.GetRandomColorUseCase
import ru.astrainteractive.astratemplate.feature.gui.domain.SetDisplayNameUseCase
import ru.astrainteractive.astratemplate.feature.gui.sample.feature.DefaultSampleGUIComponent
import ru.astrainteractive.astratemplate.feature.gui.sample.gui.SampleGUI
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import ru.astrainteractive.klibs.mikro.core.util.tryCast

@Suppress("LongParameterList")
internal class RouterImpl(
    private val ioScope: CoroutineScope,
    private val dispatchers: KotlinDispatchers,
    private val kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val translationKrate: CachedKrate<PluginTranslation>,
    private val localDao: LocalDao,
    private val itemStackSpigotAPi: ItemStackSpigotAPI,
    private val getRandomColorUseCase: GetRandomColorUseCase,
    private val setDisplayNameUseCase: SetDisplayNameUseCase
) : Router, Logger by JUtiltLogger("Router") {
    private fun buildRoute(player: Player, route: Router.Route): Inventory {
        return when (route) {
            Router.Route.Sample -> SampleGUI(
                player = player,
                kyoriKrate = kyoriKrate,
                translationKrate = translationKrate,
                sampleComponent = DefaultSampleGUIComponent(
                    localDao = localDao,
                    itemStackSpigotAPi = itemStackSpigotAPi,
                    getRandomColorUseCase = getRandomColorUseCase,
                    setDisplayNameUseCase = setDisplayNameUseCase
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
            val inventory = buildRoute(bukkitPlayer, route)
            withContext(dispatchers.Main) { bukkitPlayer.openInventory(inventory) }
        }
    }
}
