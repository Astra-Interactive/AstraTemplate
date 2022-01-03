package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.IAstraListener
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.utils.Translation
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
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
        e.player.sendMessage(Translation.instance.blockPlaced)
        return
    }
    /**
     * As said in EventHandler, every Event must have onDisable method, which disabling events
     * Here BlockPlaceEvent is unregistering
     * It's okay to not write anything here, since you call [HandlerList.unregister] in [AstraTemplate.onDisable]
     */
    public override fun onDisable() {
        BlockPlaceEvent.getHandlerList().unregister(this)
    }
}