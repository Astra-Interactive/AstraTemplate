package ru.astrainteractive.astratemplate.event.di

import org.bukkit.event.HandlerList
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.menu.event.DefaultInventoryClickEvent
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.event.BetterAnotherEvent
import ru.astrainteractive.astratemplate.event.event.MultipleEventsDSL
import ru.astrainteractive.astratemplate.event.event.TemplateEvent

internal class EventModule(rootModule: RootModule) {
    val eventListener: EventListener by lazy {
        EventListener.Default()
    }
    val inventoryClickListener: EventListener by lazy {
        DefaultInventoryClickEvent()
    }

    private val events = buildList {
        eventListener.run(::add)
        inventoryClickListener.run(::add)
        TemplateEvent(
            kyoriKrate = rootModule.bukkitModule.kyoriKrate,
            translationKrate = rootModule.coreModule.translationKrate
        ).run(::add)
        BetterAnotherEvent().run(::add)
    }

    val lifecycle: Lifecycle = Lifecycle.Lambda(
        onEnable = {
            events.forEach { it.onEnable(rootModule.bukkitModule.plugin) }
            MultipleEventsDSL(
                eventListener = eventListener,
                plugin = rootModule.bukkitModule.plugin
            )
        },
        onDisable = {
            events.forEach(EventListener::onDisable)
            HandlerList.unregisterAll(rootModule.bukkitModule.plugin)
        }
    )
}
