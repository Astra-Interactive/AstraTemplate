package com.astrainteractive.astratemplate.events.events

import com.astrainteractive.astralibs.events.EventListener
import org.bukkit.event.block.BlockBreakEvent

//Чуть удобнее
class BetterAnotherEvent: EventListener {
    @org.bukkit.event.EventHandler
    fun blockBreakEvent(e: BlockBreakEvent){
        println("blockBreakEvent ${e.player.name}")
    }
    override fun onDisable() {
        BlockBreakEvent.getHandlerList().unregister(this)
    }
}