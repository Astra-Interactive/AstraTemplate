package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.IAstraListener
import com.astrainteractive.astralibs.IAstraManager


/**
 * Handler for all your events
 */
class EventHandler() : IAstraManager {
    override val handlers: MutableList<IAstraListener> = mutableListOf()
    init {
        TemplateEvent().onEnable(this)
    }
}