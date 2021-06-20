package com.makeevrserg.empireprojekt.menumanager

import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import com.makeevrserg.empiretemplate.menumanager.Menu
import com.makeevrserg.empiretemplate.menumanager.PlayerMenuUtility


abstract class PaginatedMenu(playerMenuUtility: PlayerMenuUtility?) : Menu(playerMenuUtility!!) {
    open var page = 0
    protected var maxItemsPerPage = 45
    var maxPages: Int = 1
    protected var index = 0


    fun reloadPage(next: Int) {
        page += next
        inventory.clear()
        setMenuItems()
    }

    private fun setItem(page: String, items: MutableMap<String, ItemStack>, id: String?): ItemStack {

        id ?: return ItemStack(Material.PAPER)
        val itemStack = items[id] ?: ItemStack(Material.PAPER)
        val itemMeta = itemStack.itemMeta
        itemMeta.setDisplayName(page)
        itemStack.itemMeta = itemMeta

        return itemStack

    }


    fun addManageButtons() {
        if (page >= 1)
            inventory.setItem(
                45,
                plugin.empireMenuConfig.itemNavigationMap["prev"]!!
            )

        if (page < maxPages)
            inventory.setItem(
                53,
                plugin.empireMenuConfig.itemNavigationMap["next"]!!
            )

        inventory.setItem(
            49,
            plugin.empireMenuConfig.itemNavigationMap["back"]!!
        )

    }
}
