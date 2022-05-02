package com.astrainteractive.astratemplate.events

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.EventListener
import com.astrainteractive.astralibs.EventManager
import com.astrainteractive.astralibs.menu.MenuListener
import com.astrainteractive.astratemplate.AstraTemplate
import org.bukkit.entity.Player
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.plugin.EventExecutor
import org.bukkit.util.Consumer


/**
 * Handler for all your events
 */
class EventHandler : EventManager {
    override val handlers: MutableList<EventListener> = mutableListOf()
    private val handler: EventHandler = this
    val templateEvent = TemplateEvent().apply { onEnable(handler) }
    val menuListener = MenuListener().apply { onEnable(handler) }

}

object TestEvents {

    fun init() {
        OnMove()
    }

    fun OnMove() = event(PlayerMoveEvent::class.java) {
        print("On move ${this.player.name}")
    }


    inline fun <reified T : Event> event(
        clazz: Class<T>,
        eventManager: EventManager = GlobalEventManager,
        eventPriority: EventPriority = EventPriority.NORMAL,
        crossinline block: T.() -> Unit
    ) =
        object : EventListener {

            override fun onEnable(manager: EventManager): EventListener {
                eventManager.addHandler(this)
                AstraLibs.instance.server.pluginManager.registerEvent(
                    clazz, this, eventPriority,
                    { _, event ->
                        block(event as T)
                    }, AstraTemplate.instance
                )
                return this
            }

            override fun onDisable() {
            }

        }

    object GlobalEventManager : EventManager {
        override val handlers: MutableList<EventListener> = mutableListOf()
    }
}