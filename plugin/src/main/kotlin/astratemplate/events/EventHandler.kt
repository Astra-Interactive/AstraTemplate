package com.astrainteractive.astratemplate.events

import com.astrainteractive.astratemplate.events.events.MultipleEventsDSL
import com.astrainteractive.astratemplate.events.events.TemplateEvent
import ru.astrainteractive.astralibs.events.EventListener
import ru.astrainteractive.astralibs.events.EventManager


/**
 * Handler for all your events
 */
class EventHandler : EventManager {
    override val handlers: MutableList<EventListener> = mutableListOf()
    private val handler: EventHandler = this
    val templateEvent = TemplateEvent().apply { onEnable(handler) }

    init {
        MultipleEventsDSL()
    }
}
