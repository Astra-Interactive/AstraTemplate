package ru.astrainteractive.astratemplate.event.block.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.launch
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.event.flowEvent
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

internal class BlockBreakLogEvent(
    plugin: Plugin,
    mainScope: CoroutineScope
) : Logger by JUtiltLogger("AstraTemplate-BlockBreakLogEvent") {
    val blockBreakEvent: Job = mainScope.launch(start = CoroutineStart.LAZY) {
        flowEvent<BlockBreakEvent>(plugin = plugin)
            .filterIsInstance<BlockBreakEvent>()
            .collect { event -> info { "#blockBreakEvent ${event.player.name}" } }
    }
}
