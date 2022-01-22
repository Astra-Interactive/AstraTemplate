package com.astrainteractive.astratemplate.gui

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.menu.AstraMenuSize
import com.astrainteractive.astralibs.menu.AstraPlayerMenuUtility
import com.astrainteractive.astralibs.menu.Menu
import com.astrainteractive.astralibs.menu.PaginatedMenu
import com.astrainteractive.astralibs.observer.LifecycleOwner
import com.astrainteractive.astratemplate.api.Api
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.ItemStack

class AstraGui(override val playerMenuUtility: AstraPlayerMenuUtility) : PaginatedMenu(), Listener, LifecycleOwner {

    override var menuName: String = "Меню"
    override val menuSize: AstraMenuSize = AstraMenuSize.XL
    override val backPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName("Закрыть")
        itemMeta = meta
    }
    override val nextPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName("Вперед")
        itemMeta = meta
    }
    override val prevPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName("Назад")
        itemMeta = meta
    }
    override var maxItemsPerPage: Int = 45
    override var page: Int = 0
    private val itemsInGui = Api.items
    override val maxItemsAmount: Int
        get() = Api.items.value?.size ?: 0


    override fun handleMenu(e: InventoryClickEvent) {
        super.handleMenu(e)
    }

    init {
        itemsInGui.observe(this) { items ->
            setMenuItems()
        }
        Bukkit.getServer().pluginManager.registerEvents(this, AstraLibs.instance)
    }

    @EventHandler
    private fun onMenuClose(e: InventoryCloseEvent) {
        if (e.inventory.holder is Menu || e.inventory.holder is PaginatedMenu) {
            onMenuClosed()
            InventoryCloseEvent.getHandlerList().unregister(this)
        }
    }

    private fun onMenuClosed() {
        itemsInGui.removeObserver()
    }

    override fun setMenuItems() {
        val list = itemsInGui.value
        list ?: return
        inventory.clear()
        addManageButtons()

        for (i in 0 until maxItemsPerPage) {
            val index = maxItemsPerPage * page + i
            if (index >= list.size)
                continue
            val itemStack = list[index]
            itemStack.apply {
                val meta = itemMeta ?: return@apply
                meta.setDisplayName("${ChatColor.YELLOW}GUI: ${list[index]}")
                itemMeta = meta
            }
            inventory.setItem(i, itemStack)
        }
    }


}