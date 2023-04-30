package ru.astrainteractive.astratemplate.events.events

import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import ru.astrainteractive.astralibs.events.DSLEvent
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.events.di.EventModule

/**
 * This is a most convenient way to use bukkit events in kotlin
 */
class MultipleEventsDSL(module: EventModule) {
    private val eventListener by module.eventListener
    private val plugin by module.plugin

    val blockBreakEvent = DSLEvent<BlockBreakEvent>(eventListener, plugin) {
        println("DSLEvent: blockBreakEvent ${it.player.name}")
    }

    val entityDamageEvent = DSLEvent<EntityDamageEvent>(eventListener, plugin) {
        println("DSLEvent: entityDamageEvent ${it.entity.name}")
    }
}
