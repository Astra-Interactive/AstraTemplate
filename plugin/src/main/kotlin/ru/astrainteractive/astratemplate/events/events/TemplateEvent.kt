package ru.astrainteractive.astratemplate.events.events

import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.modules.TranslationProvider
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockPlaceEvent
import ru.astrainteractive.astralibs.events.EventListener


/**
 * Template event class
 * @see [MultipleEventsDSL]
 */
class TemplateEvent : EventListener {

    /**
     * Sample event which is called when Block is placed
     */
    @EventHandler
    public fun blockPlaceEvent(e: BlockPlaceEvent) {
        e.player.sendMessage(TranslationProvider.value.blockPlaced)
        return
    }

    /**
     * As said in EventHandler, every Event must have onDisable method, which disabling events
     * Here BlockPlaceEvent is unregistering
     * It's okay to not write anything here, since you call [HandlerList.unregister] in [AstraTemplate.onDisable]
     */
    public override fun onDisable() {
        BlockPlaceEvent.getHandlerList().unregister(this)
    }
}