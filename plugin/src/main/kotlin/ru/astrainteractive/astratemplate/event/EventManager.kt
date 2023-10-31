package ru.astrainteractive.astratemplate.event

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astratemplate.event.di.EventDependencies
import ru.astrainteractive.astratemplate.event.event.BetterAnotherEvent
import ru.astrainteractive.astratemplate.event.event.MultipleEventsDSL
import ru.astrainteractive.astratemplate.event.event.TemplateEvent

/**
 * Handler for all your events
 */
class EventManager(
    private val dependencies: EventDependencies
) : EventListener {
    private val events = buildList {
        TemplateEvent(dependencies).also(::add)
        BetterAnotherEvent().also(::add)
    }

    override fun onEnable(plugin: Plugin) {
        super.onEnable(plugin)
        events.forEach { it.onEnable(plugin) }
        // DSL Events
        MultipleEventsDSL(dependencies)
    }

    override fun onDisable() {
        super.onDisable()
        events.forEach(EventListener::onDisable)
    }
}
