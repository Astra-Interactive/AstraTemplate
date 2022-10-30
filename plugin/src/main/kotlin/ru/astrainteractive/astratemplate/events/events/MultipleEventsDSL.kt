package ru.astrainteractive.astratemplate.events.events

import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import ru.astrainteractive.astralibs.events.DSLEvent

/**
 * This is a most convenient way to use bukkit events in kotlin
 */
class MultipleEventsDSL {
    val blockBreakEvent = DSLEvent.event(BlockBreakEvent::class.java) {
        println("blockBreakEvent ${it.player.name}")
    }

    val entityDamageEvent = DSLEvent.event(EntityDamageEvent::class.java) {
        println("entityDamageEvent ${it.entity.name}")
    }
}

