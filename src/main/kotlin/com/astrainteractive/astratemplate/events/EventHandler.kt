package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.events.EventListener
import com.astrainteractive.astralibs.events.EventManager
import com.astrainteractive.astralibs.menu.MenuListener
import com.astrainteractive.astratemplate.AstraTemplate
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.block.BlockBreakEvent


/**
 * Handler for all your events
 */
class EventHandler : EventManager {
    override val handlers: MutableList<EventListener> = mutableListOf()
    private val handler: EventHandler = this
    val templateEvent = TemplateEvent().apply { onEnable(handler) }
    val menuListener = MenuListener().apply { onEnable(handler) }
    init {
        MultipleEventsDSL()

    }

}
