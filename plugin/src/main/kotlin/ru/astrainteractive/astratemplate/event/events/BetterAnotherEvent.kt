package ru.astrainteractive.astratemplate.event.events

import org.bukkit.event.block.BlockBreakEvent
import ru.astrainteractive.astralibs.event.EventListener

/**
 * This is a more convenient way with base class
 */
class BetterAnotherEvent : EventListener {
    @org.bukkit.event.EventHandler
    fun blockBreakEvent(e: BlockBreakEvent) {
        println("blockBreakEvent ${e.player.name}")
    }
}
