package ru.astrainteractive.astratemplate.feature.event.di

import org.bukkit.event.HandlerList
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.feature.event.event.BetterAnotherEvent
import ru.astrainteractive.astratemplate.feature.event.event.TemplateEvent

class EventModule(
    coreModule: CoreModule,
    plugin: JavaPlugin
) {
    val eventListener: EventListener = EventListener.Default()
    val inventoryClickListener: EventListener = DefaultInventoryClickEvent()

    private val events = buildList {
        eventListener.run(::add)
        inventoryClickListener.run(::add)
        TemplateEvent(
            kyoriKrate = coreModule.kyoriKrate,
            translationKrate = coreModule.translationKrate
        ).run(::add)
        BetterAnotherEvent().run(::add)
    }

    val lifecycle: Lifecycle = Lifecycle.Lambda(
        onEnable = {
            events.forEach { listener -> listener.onEnable(plugin) }
        },
        onDisable = {
            events.forEach(EventListener::onDisable)
            HandlerList.unregisterAll(plugin)
        }
    )
}
