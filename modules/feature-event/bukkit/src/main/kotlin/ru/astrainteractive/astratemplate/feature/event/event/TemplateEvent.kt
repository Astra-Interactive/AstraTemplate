package ru.astrainteractive.astratemplate.feature.event.event

import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockPlaceEvent
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.server.util.asKAudience
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

/**
 * Template event class
 */
internal class TemplateEvent(
    translationKrate: CachedKrate<PluginTranslation>
) : EventListener {
    private val translation by translationKrate

    /**
     * Sample event which is called when Block is placed
     */
    @EventHandler
    public fun blockPlaceEvent(e: BlockPlaceEvent) {
        e.player.asKAudience().sendMessage(translation.custom.blockPlaced)
        return
    }

    /**
     * As said in EventHandler, every Event must have onDisable method, which disabling events
     * Here BlockPlaceEvent is unregistering
     * It's okay to not write anything here, since you call [HandlerList.unregister] in [ru.astrainteractive.astralibs.lifecycle.Lifecycle.onDisable]
     */
    public override fun onDisable() {
        super.onDisable()
        BlockPlaceEvent.getHandlerList().unregister(this)
    }
}
