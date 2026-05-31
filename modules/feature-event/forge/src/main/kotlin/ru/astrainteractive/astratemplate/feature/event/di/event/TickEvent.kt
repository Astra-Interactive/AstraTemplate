package ru.astrainteractive.astratemplate.feature.event.di.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import net.minecraftforge.event.TickEvent
import ru.astrainteractive.astralibs.event.flowEvent
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger
import ru.astrainteractive.klibs.mikro.core.logging.Logger
import kotlin.time.Duration.Companion.seconds

class TickEvent(mainScope: CoroutineScope) : Logger by JUtiltLogger("AstraTemplate-TickEvent") {
    val tickEvent = flowEvent<TickEvent>()
        .debounce(1.seconds)
        .onEach { info { "#tickEvent -> flow onEach with 1.second debounce" } }
        .launchIn(mainScope)
}
