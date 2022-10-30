package ru.astrainteractive.astratemplate.events

import ru.astrainteractive.astratemplate.events.events.MultipleEventsDSL
import ru.astrainteractive.astratemplate.events.events.TemplateEvent
import ru.astrainteractive.astralibs.events.EventListener
import ru.astrainteractive.astralibs.events.EventManager
import ru.astrainteractive.astratemplate.events.events.BetterAnotherEvent


/**
 * Handler for all your events
 */
class EventHandler : EventManager {
    override val handlers: MutableList<EventListener> = mutableListOf()
    private val handler: EventHandler = this
    val templateEvent = TemplateEvent().apply { onEnable(handler) }

    init {
        MultipleEventsDSL()
        BetterAnotherEvent().onEnable(this)
    }
}
