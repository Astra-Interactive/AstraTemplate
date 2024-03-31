package ru.astrainteractive.astratemplate.event.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.event.TickEvent
import ru.astrainteractive.astratemplate.event.core.ForgeEventBusListener

interface EventModule {
    val lifecycle: Lifecycle

    class Default : EventModule {
        private val events: List<ForgeEventBusListener> by lazy {
            listOf(
                TickEvent()
            )
        }
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    events.forEach(ForgeEventBusListener::register)
                },
                onDisable = {
                    events.forEach(ForgeEventBusListener::unregister)
                }
            )
        }
    }
}
