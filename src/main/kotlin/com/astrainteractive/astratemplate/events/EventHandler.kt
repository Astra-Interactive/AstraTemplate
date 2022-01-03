package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.IAstraListener
import com.astrainteractive.astralibs.IAstraManager
import com.astrainteractive.astralibs.menu.MenuListener


/**
 * Handler for all your events
 */
class EventHandler : IAstraManager {
    override val handlers: MutableList<IAstraListener> = mutableListOf()
    private val handler: EventHandler = this
    val templateEvent = TemplateEvent().apply { onEnable(handler) }
    val menuListener = MenuListener().apply { onEnable(handler) }

}