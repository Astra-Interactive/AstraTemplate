package ru.astrainteractive.astratemplate.event.bukkit.block.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.event.flowEvent
import ru.astrainteractive.astralibs.server.util.asKAudience
import ru.astrainteractive.astratemplate.core.plugin.model.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

internal class BlockPlaceMessageEvent(
    plugin: Plugin,
    mainScope: CoroutineScope,
    translationKrate: CachedKrate<PluginTranslation>
) {
    private val translation by translationKrate

    val blockPlaceEvent: Job = mainScope.launch(start = CoroutineStart.LAZY) {
        flowEvent<BlockPlaceEvent>(plugin = plugin)
            .filterIsInstance<BlockPlaceEvent>()
            .collect { event -> event.player.asKAudience().sendMessage(translation.blockPlace.message) }
    }
}
