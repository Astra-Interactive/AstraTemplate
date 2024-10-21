package ru.astrainteractive.astratemplate.event.event

import org.bukkit.event.block.BlockBreakEvent
import ru.astrainteractive.astralibs.event.EventListener

/**
 * This is a more convenient way with base class
 */
internal class BetterAnotherEvent : EventListener {
    @org.bukkit.event.EventHandler
    fun blockBreakEvent(e: BlockBreakEvent) {
        println("blockBreakEvent ${e.player.name}")
    }
}
