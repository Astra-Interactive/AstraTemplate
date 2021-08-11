package com.makeevrserg.empiretemplate.empirelibs.menu

import com.makeevrserg.empiretemplate.EmpireTemplate
import empirelibs.menu.Menu
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
/**
 * You probably won't ever edit this file
 */
class MenuListener : Listener {

    /**
     * Cancelling inventory event if player clicked
     */
    @EventHandler
    fun onMenuClick(e: InventoryClickEvent) {
        val holder = e.clickedInventory?.holder?:return
        if (e.view.topInventory.holder is Menu)
            e.isCancelled = true
        if (holder is Menu || holder is PaginatedMenu)
            e.isCancelled = true
        if (holder is Menu) {
            e.currentItem?:return
            holder.handleMenu(e)
        }
    }


    init {
        EmpireTemplate.instance.server.pluginManager.registerEvents(this, EmpireTemplate.instance)
    }

    public fun onDisable(){
        InventoryClickEvent.getHandlerList().unregister(this)
    }
}