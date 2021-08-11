package com.makeevrserg.empiretemplate.events

import com.makeevrserg.empiretemplate.EmpireTemplate
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent


/**
 * Template event class
 */
class TemplateEvent : Listener {

    /**
     * Sample event which is called when Block is placed
     */
    @EventHandler
    public fun blockPlaceEvent(e: BlockPlaceEvent) {
        return
    }

    /**
     * Here you should add listener to this class
     */
    init {
        EmpireTemplate.instance.server.pluginManager.registerEvents(this, EmpireTemplate.instance)

    }
    /**
     * As said in EventHandler, every Event must have onDisable method, which disabling events
     * Here BlockPlaceEvent is unregistering
     * @see blockPlaceEvent
     * @see EventHandler
     */
    public fun onDisable() {
        BlockPlaceEvent.getHandlerList().unregister(this)
    }
}