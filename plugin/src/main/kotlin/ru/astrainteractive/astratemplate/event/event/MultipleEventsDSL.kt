package ru.astrainteractive.astratemplate.event.event

import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import ru.astrainteractive.astralibs.event.DSLEvent
import ru.astrainteractive.astratemplate.event.di.EventModule

/**
 * This is a most convenient way to use bukkit events in kotlin
 */
class MultipleEventsDSL(module: EventModule) : EventModule by module {

    val blockBreakEvent = DSLEvent<BlockBreakEvent>(eventListener, plugin) {
        println("DSLEvent: blockBreakEvent ${it.player.name}")
    }

    val entityDamageEvent = DSLEvent<EntityDamageEvent>(eventListener, plugin) {
        println("DSLEvent: entityDamageEvent ${it.entity.name}")
    }
}
