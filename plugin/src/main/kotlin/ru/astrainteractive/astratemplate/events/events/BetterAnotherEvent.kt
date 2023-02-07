package ru.astrainteractive.astratemplate.events.events

import org.bukkit.event.block.BlockBreakEvent
import ru.astrainteractive.astralibs.events.EventListener

/**
 * This is a more convenient way with base class
 */
class BetterAnotherEvent : EventListener {
    @org.bukkit.event.EventHandler
    fun blockBreakEvent(e: BlockBreakEvent) {
        println("blockBreakEvent ${e.player.name}")
    }

    override fun onDisable() {
        super.onDisable()
        BlockBreakEvent.getHandlerList().unregister(this)
    }
}
