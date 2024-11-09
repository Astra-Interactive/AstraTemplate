package ru.astrainteractive.astratemplate.event.di

import org.bukkit.event.HandlerList
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.event.BetterAnotherEvent
import ru.astrainteractive.astratemplate.event.event.MultipleEventsDSL
import ru.astrainteractive.astratemplate.event.event.TemplateEvent

internal interface EventModule {
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

        val dependencies: EventDependencies = EventDependencies.Default(rootModule, eventListener)

        private val events = buildList {
            eventListener.run(::add)
            inventoryClickListener.run(::add)
            TemplateEvent(dependencies).run(::add)
            BetterAnotherEvent().run(::add)
        }

        override val lifecycle: Lifecycle = Lifecycle.Lambda(
            onEnable = {
                events.forEach { it.onEnable(dependencies.plugin) }
                MultipleEventsDSL(dependencies)
            },
            onDisable = {
                events.forEach(EventListener::onDisable)
                HandlerList.unregisterAll(dependencies.plugin)
            }
        )
    }
}
