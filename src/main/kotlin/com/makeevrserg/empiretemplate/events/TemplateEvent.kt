package com.makeevrserg.empiretemplate.events

import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent

class TemplateEvent : Listener {
    @EventHandler
    public fun blockPlaceEvent(e: BlockPlaceEvent) {
        return
    }

    init {
        plugin.server.pluginManager.registerEvents(this, plugin)

    }

    public fun onDisable() {
        BlockPlaceEvent.getHandlerList().unregister(this)
    }
}