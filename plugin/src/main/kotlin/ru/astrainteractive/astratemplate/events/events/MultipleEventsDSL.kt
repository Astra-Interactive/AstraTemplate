package ru.astrainteractive.astratemplate.events.events

import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import ru.astrainteractive.astralibs.events.DSLEvent

/**
 * This is a most convenient way to use bukkit events in kotlin
 */
class MultipleEventsDSL {
    val blockBreakEvent = DSLEvent.event<BlockBreakEvent> {
        println("blockBreakEvent ${it.player.name}")
    }

    val entityDamageEvent = DSLEvent.event<EntityDamageEvent>{
        println("entityDamageEvent ${it.entity.name}")
    }
}

