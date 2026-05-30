package ru.astrainteractive.astratemplate.feature.event.event

import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.block.BlockPlaceEvent
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

/**
 * Template event class
 */
internal class TemplateEvent(
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    translationKrate: CachedKrate<PluginTranslation>
) : EventListener,
    KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    /**
     * Sample event which is called when Block is placed
     */
    @EventHandler
    public fun blockPlaceEvent(e: BlockPlaceEvent) {
        e.player.sendMessage(translation.custom.blockPlaced.let(::toComponent))
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
