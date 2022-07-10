package com.astrainteractive.astratemplate.events.events

import com.astrainteractive.astralibs.events.DSLEvent
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

