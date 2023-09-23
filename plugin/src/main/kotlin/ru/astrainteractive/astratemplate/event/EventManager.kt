package ru.astrainteractive.astratemplate.event

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.event.event.BetterAnotherEvent
import ru.astrainteractive.astratemplate.event.event.MultipleEventsDSL
import ru.astrainteractive.astratemplate.event.event.TemplateEvent

/**
 * Handler for all your events
 */
class EventManager(
    private val module: EventModule
) : EventListener {
    private val events = buildList {
        TemplateEvent(module).also(::add)
        BetterAnotherEvent().also(::add)
    }

    override fun onEnable(plugin: Plugin) {
        super.onEnable(plugin)
        events.forEach { it.onEnable(plugin) }
        // DSL Events
        MultipleEventsDSL(module)
    }

    override fun onDisable() {
        super.onDisable()
        events.forEach(EventListener::onDisable)
    }
}
