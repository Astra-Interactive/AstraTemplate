package ru.astrainteractive.astratemplate.events

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astratemplate.events.events.MultipleEventsDSL
import ru.astrainteractive.astratemplate.events.events.TemplateEvent
import ru.astrainteractive.astralibs.events.EventListener
import ru.astrainteractive.astratemplate.events.events.BetterAnotherEvent


/**
 * Handler for all your events
 */
class EventManager : EventListener {
    private val events = buildList {
        add(TemplateEvent())
        add(BetterAnotherEvent())
    }

    override fun onEnable(plugin: Plugin) {
        super.onEnable(plugin)
        events.forEach { it.onEnable(plugin) }
        // DSL Events
        MultipleEventsDSL()
    }

    override fun onDisable() {
        super.onDisable()
        events.forEach(EventListener::onDisable)
    }
}
