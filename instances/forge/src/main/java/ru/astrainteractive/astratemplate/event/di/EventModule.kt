package ru.astrainteractive.astratemplate.event.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.event.TickEvent

class EventModule(coreModule: CoreModule) {
    @Suppress("UnusedPrivateProperty")
    private val tickEvent = TickEvent(coreModule.mainScope)
    val lifecycle: Lifecycle = Lifecycle.Empty
}
