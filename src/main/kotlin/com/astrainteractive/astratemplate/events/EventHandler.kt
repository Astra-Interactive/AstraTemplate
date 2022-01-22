package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.EventListener
import com.astrainteractive.astralibs.EventManager
import com.astrainteractive.astralibs.menu.MenuListener


/**
 * Handler for all your events
 */
class EventHandler : EventManager {
    override val handlers: MutableList<EventListener> = mutableListOf()
    private val handler: EventHandler = this
    val templateEvent = TemplateEvent().apply { onEnable(handler) }
    val menuListener = MenuListener().apply { onEnable(handler) }

}