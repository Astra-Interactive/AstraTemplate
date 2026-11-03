package ru.astrainteractive.astratemplate.event.bukkit.di

import kotlinx.coroutines.Job
import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.event.bukkit.block.event.BlockBreakLogEvent
import ru.astrainteractive.astratemplate.event.bukkit.block.event.BlockPlaceMessageEvent

class EventModule(
    coreModule: CoreModule,
    plugin: JavaPlugin
) {
    val eventListener: EventListener = EventListener.Default()
    val inventoryClickListener: EventListener = DefaultInventoryClickEvent()

    private val blockPlaceMessageEvent = BlockPlaceMessageEvent(
        plugin = plugin,
        mainScope = coreModule.mainScope,
        translationKrate = coreModule.translationKrate
    )

    private val blockBreakLogEvent = BlockBreakLogEvent(
        plugin = plugin,
        mainScope = coreModule.mainScope
    )

    private val listeners: List<EventListener>
        get() = listOf(eventListener, inventoryClickListener)

    private val eventJobs: List<Job>
        get() = listOf(blockPlaceMessageEvent.blockPlaceEvent, blockBreakLogEvent.blockBreakEvent)

    val lifecycle: Lifecycle = Lifecycle.Lambda(
        onEnable = {
            listeners.forEach { listener -> listener.onEnable(plugin) }
            eventJobs.forEach(Job::start)
        },
        onDisable = {
            eventJobs.forEach(Job::cancel)
            listeners.forEach(EventListener::onDisable)
            HandlerList.unregisterAll(plugin)
        }
    )
}
