package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.IAstraListener
import com.astrainteractive.astratemplate.AstraTemplate
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockPlaceEvent


/**
 * Template event class
 */
class TemplateEvent : IAstraListener {

    /**
     * Sample event which is called when Block is placed
     */
    @EventHandler
    public fun blockPlaceEvent(e: BlockPlaceEvent) {
        return
    }
    /**
     * As said in EventHandler, every Event must have onDisable method, which disabling events
     * Here BlockPlaceEvent is unregistering
     * @see blockPlaceEvent
     * @see EventHandler
     */
    public override fun onDisable() {
        BlockPlaceEvent.getHandlerList().unregister(this)
    }
}