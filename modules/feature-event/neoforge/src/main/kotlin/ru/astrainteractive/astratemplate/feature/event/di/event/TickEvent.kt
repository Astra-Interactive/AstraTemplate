package ru.astrainteractive.astratemplate.feature.event.di.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.neoforged.neoforge.event.tick.ServerTickEvent
import ru.astrainteractive.astralibs.event.flowEvent
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger

class TickEvent(mainScope: CoroutineScope) : Logger by JUtiltLogger("AstraTemplate-TickEvent") {
    val tickEvent = flowEvent<ServerTickEvent>()
        .onEach { info { "#tickEvent -> flow onEach" } }
        .launchIn(mainScope)
}