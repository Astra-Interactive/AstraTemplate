package ru.astrainteractive.astratemplate.event.event

import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import ru.astrainteractive.astralibs.event.DSLEvent
import ru.astrainteractive.astratemplate.event.di.EventDependencies

/**
 * This is a most convenient way to use bukkit events in kotlin
 */
internal class MultipleEventsDSL(module: EventDependencies) : EventDependencies by module {

    val blockBreakEvent = DSLEvent<BlockBreakEvent>(eventListener, plugin) {
        println("DSLEvent: blockBreakEvent ${it.player.name}")
    }

    val entityDamageEvent = DSLEvent<EntityDamageEvent>(eventListener, plugin) {
        println("DSLEvent: entityDamageEvent ${it.entity.name}")
    }
}
