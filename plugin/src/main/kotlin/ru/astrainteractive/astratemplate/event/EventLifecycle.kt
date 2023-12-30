package ru.astrainteractive.astratemplate.event

import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.event.di.EventDependencies
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.event.event.BetterAnotherEvent
import ru.astrainteractive.astratemplate.event.event.MultipleEventsDSL
import ru.astrainteractive.astratemplate.event.event.TemplateEvent

/**
 * Handler for all your events
 */
internal class EventLifecycle(
    private val eventModule: EventModule,
    private val dependencies: EventDependencies
) : Lifecycle {
    private val defaultStyleEvents = buildList {
        eventModule.eventListener.also(::add)
        eventModule.inventoryClickListener.also(::add)
        TemplateEvent(dependencies).also(::add)
        BetterAnotherEvent().also(::add)
    }

    override fun onEnable() {
        defaultStyleEvents.forEach { it.onEnable(dependencies.plugin) }
        // DSL Events
        MultipleEventsDSL(dependencies)
    }

    override fun onDisable() {
        defaultStyleEvents.forEach(EventListener::onDisable)
    }
}
