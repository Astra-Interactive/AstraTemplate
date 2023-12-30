package ru.astrainteractive.astratemplate.event.di

import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.EventLifecycle

interface EventModule {
    val eventListener: EventListener
    val inventoryClickListener: EventListener
    val lifecycle: Lifecycle

    class Default(rootModule: RootModule) : EventModule {
        override val eventListener: EventListener by lazy {
            EventListener.Default()
        }
        override val inventoryClickListener: EventListener by lazy {
            DefaultInventoryClickEvent()
        }
        override val lifecycle: Lifecycle by lazy {
            val dependencies: EventDependencies = EventDependencies.Default(rootModule, eventListener)
            EventLifecycle(this, dependencies)
        }
    }
}
