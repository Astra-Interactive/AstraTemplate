package ru.astrainteractive.astratemplate.events

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.events.EventListener
import ru.astrainteractive.astratemplate.events.events.BetterAnotherEvent
import ru.astrainteractive.astratemplate.events.events.MultipleEventsDSL
import ru.astrainteractive.astratemplate.events.events.TemplateEvent
import ru.astrainteractive.astratemplate.plugin.Translation

/**
 * Handler for all your events
 */
class EventManager(
    translationModule: Dependency<Translation>
) : EventListener {
    private val events = buildList {
        TemplateEvent(
            translationModule = translationModule
        ).also(::add)
        BetterAnotherEvent().also(::add)
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
