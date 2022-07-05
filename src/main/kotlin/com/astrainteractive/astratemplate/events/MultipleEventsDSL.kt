package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.events.DSLEvent
import com.astrainteractive.astralibs.events.EventListener
import org.bukkit.Bukkit
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
// Прекрасно замечательно идеально
class MultipleEventsDSL {
    val blockBreakEvent = DSLEvent.event(BlockBreakEvent::class.java) {
        println("blockBreakEvent ${it.player.name}")
    }

    val entityDamageEvent = DSLEvent.event(EntityDamageEvent::class.java) {
        println("entityDamageEvent ${it.entity.name}")
    }
}
// Неудобно
class AnotherEvent:Listener{
    init {
        AstraLibs.instance.server.pluginManager.registerEvents(this, AstraLibs.instance)
    }
    @org.bukkit.event.EventHandler
    fun blockBreakEvent(e:BlockBreakEvent){
        println("blockBreakEvent ${e.player.name}")
    }
    fun onDisable(){
        BlockBreakEvent.getHandlerList().unregister(this)
        HandlerList.unregisterAll(this)
    }
}
//Чуть удобнее
class BetterAnotherEvent: EventListener {
    @org.bukkit.event.EventHandler
    fun blockBreakEvent(e:BlockBreakEvent){
        println("blockBreakEvent ${e.player.name}")
    }
    override fun onDisable() {
        BlockBreakEvent.getHandlerList().unregister(this)
    }
}