package ru.astrainteractive.astratemplate.feature.event.event

import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent
import ru.astrainteractive.astralibs.event.EventListener

/**
 * This is a more convenient way with base class
 */
internal class BetterAnotherEvent : EventListener {
    @EventHandler
    fun blockBreakEvent(e: BlockBreakEvent) {
        println("blockBreakEvent ${e.player.name}")
    }
}
