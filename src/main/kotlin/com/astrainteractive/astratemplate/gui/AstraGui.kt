package com.astrainteractive.astratemplate.gui
import com.astrainteractive.astralibs.menu.PaginatedMenu
import com.astrainteractive.astralibs.menu.PlayerMenuUtility
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

class AstraGui(playerMenuUtility: PlayerMenuUtility): PaginatedMenu(playerMenuUtility) {

    override var menuName: String = "Меню"
    override val menuSize: Int = 54
    override val backPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName("Закрыть")
        itemMeta = meta
    }
    override var maxItemsPerPage: Int = 45
    override var maxPages: Int = getMaxPages()
    override val nextPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName("Вперед")
        itemMeta = meta
    }
    override var page: Int = 0
    override val prevPageButton: ItemStack = ItemStack(Material.PAPER).apply {
        val meta = itemMeta
        meta.setDisplayName("Назад")
        itemMeta = meta
    }
    private val itemsInGui = listOf("Предмет 1","Предмет 2")
    override var slotsAmount: Int = itemsInGui.size

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