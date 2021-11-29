package com.astrainteractive.astratemplate.gui
import com.astrainteractive.astralibs.menu.AstraMenuSize
import com.astrainteractive.astralibs.menu.AstraPlayerMenuUtility
import com.astrainteractive.astralibs.menu.PaginatedMenu
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class AstraGui(override val playerMenuUtility: AstraPlayerMenuUtility): PaginatedMenu() {

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
    private val itemsInGui = listOf("Предмет 1","Предмет 2")
    override val maxItemsAmount: Int = itemsInGui.size


    override fun handleMenu(e: InventoryClickEvent) {
        super.handleMenu(e)
    }
    override fun setMenuItems() {
        for (i in 0 until maxItemsPerPage){
            val index = maxItemsPerPage * page + i
            if (index>=itemsInGui.size)
                continue

            val itemStack = ItemStack(Material.OAK_LOG).apply {
                val meta = itemMeta
                meta.setDisplayName("${ChatColor.YELLOW}GUI: ${itemsInGui[index]}")
                itemMeta = meta
            }
            inventory.setItem(i,itemStack)
        }
    }

}