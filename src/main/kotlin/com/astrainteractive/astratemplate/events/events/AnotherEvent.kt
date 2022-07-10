package com.astrainteractive.astratemplate.events.events

import com.astrainteractive.astralibs.AstraLibs
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

// Неудобно
class AnotherEvent: Listener {
    init {
        AstraLibs.instance.server.pluginManager.registerEvents(this, AstraLibs.instance)
    }
    @org.bukkit.event.EventHandler
    fun blockBreakEvent(e: BlockBreakEvent){
        println("blockBreakEvent ${e.player.name}")
    }
    fun onDisable(){
        BlockBreakEvent.getHandlerList().unregister(this)
        HandlerList.unregisterAll(this)
    }
}