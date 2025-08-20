package ru.astrainteractive.astratemplate.event.event

import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.event.inlineEvent

/**
 * This is a most convenient way to use bukkit events in kotlin
 */
internal class MultipleEventsDSL(
    eventListener: EventListener,
    plugin: JavaPlugin
) {

    val blockBreakEvent = inlineEvent<BlockBreakEvent>(eventListener, plugin) {
        println("DSLEvent: blockBreakEvent ${it.player.name}")
    }

    val entityDamageEvent = inlineEvent<EntityDamageEvent>(eventListener, plugin) {
        println("DSLEvent: entityDamageEvent ${it.entity.name}")
    }
}
