package ru.astrainteractive.astratemplate.gui.router

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.api.local.dao.LocalDao
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.gui.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.gui.domain.GetRandomColorUseCase
import ru.astrainteractive.astratemplate.gui.domain.SetDisplayNameUseCase
import ru.astrainteractive.astratemplate.gui.sample.feature.DefaultSampleGUIComponent
import ru.astrainteractive.astratemplate.gui.sample.gui.SampleGUI
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

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
) : Router {
    private fun buildRoute(player: Player, route: Router.Route) = when (route) {
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
        )
    }

    override fun open(player: Player, route: Router.Route) {
        ioScope.launch {
            val gui = buildRoute(player, route)
            withContext(dispatchers.Main) { gui.open() }
        }
    }
}
