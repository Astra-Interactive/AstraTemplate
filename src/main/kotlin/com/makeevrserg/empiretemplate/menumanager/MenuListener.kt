package com.makeevrserg.empireprojekt.menumanager

import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import com.makeevrserg.empiretemplate.menumanager.Menu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class MenuListener() : Listener {
    @EventHandler
    fun onMenuClick(e: InventoryClickEvent) {
        val holder = e.clickedInventory?.holder ?: return

        if (e.view.topInventory.holder is Menu)
            e.isCancelled = true

        if (holder is Menu || holder is PaginatedMenu)
            e.isCancelled = true

        if (holder !is Menu)
            return

        e.currentItem ?: return
        holder.handleMenu(e)

    }


    init {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    public fun onDisable() {
        InventoryClickEvent.getHandlerList().unregister(this)
    }
}