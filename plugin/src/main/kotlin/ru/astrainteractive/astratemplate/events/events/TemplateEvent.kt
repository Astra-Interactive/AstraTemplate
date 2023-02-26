package ru.astrainteractive.astratemplate.events.events

import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockPlaceEvent
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astralibs.events.EventListener
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.plugin.Translation

/**
 * Template event class
 * @see [MultipleEventsDSL]
 */
class TemplateEvent(
    translationModule: Dependency<Translation>
) : EventListener {
    private val translation by translationModule

    /**
     * Sample event which is called when Block is placed
     */
    @EventHandler
    public fun blockPlaceEvent(e: BlockPlaceEvent) {
        e.player.sendMessage(translation.blockPlaced)
        return
    }

    /**
     * As said in EventHandler, every Event must have onDisable method, which disabling events
     * Here BlockPlaceEvent is unregistering
     * It's okay to not write anything here, since you call [HandlerList.unregister] in [AstraTemplate.onDisable]
     */
    public override fun onDisable() {
        super.onDisable()
        BlockPlaceEvent.getHandlerList().unregister(this)
    }
}
