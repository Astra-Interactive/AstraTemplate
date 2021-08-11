package com.makeevrserg.empiretemplate.empirelibs.menu


import com.makeevrserg.empiretemplate.EmpireTemplate
import com.makeevrserg.empiretemplate.empirelibs.HEX
import com.makeevrserg.empiretemplate.empirelibs.setDisplayName
import empirelibs.menu.Menu
import empirelibs.menu.PlayerMenuUtility
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * Menu with multiple pages
 */
abstract class PaginatedMenu(playerMenuUtility: PlayerMenuUtility?) : Menu(playerMenuUtility!!) {

    /**
     * Page of current menu. Must be 0 by default
     */
    abstract var page:Int
    /**
     * Max items allowed in current page. No more than 45 for paginated. Final row is for pagination
     */
    abstract var maxItemsPerPage:Int
    /**
     * Max items in section to display
     */
    abstract var slotsAmount:Int
    /**
     * Must be owerwritten with getMaxPages()
     */
    abstract var maxPages: Int

    @JvmName("getMaxPages1")
    public fun getMaxPages(): Int {
        return  slotsAmount/maxItemsPerPage
    }
    /**
     * Index of current item
     */
    fun getIndex(i:Int): Int {
        return maxItemsPerPage * page + i
    }

    /**
     * Check for first page
     */
    fun isFirstPage(): Boolean {
        if (page == 0) {
            playerMenuUtility.player
                .sendMessage(EmpireTemplate.translation.FIRST_PAGE)
            return true
        }
        return false
    }
    /**
     * Check for last page
     */
    fun isLastPage(): Boolean {
        if (page >= maxPages) {
            playerMenuUtility.player
                .sendMessage(EmpireTemplate.translation.LAST_PAGE)
            return true
        }
        return false
    }
    /**
     * Function for handling pages
     */
    fun loadPage(next: Int) {
        page += next
        inventory.clear()
        setMenuItems()
    }

    private fun setManageButton(name: String, material:Material): ItemStack {
        val itemStack = ItemStack(material)
        itemStack.setDisplayName(name.HEX())
        return itemStack
    }


    public fun getPrevButtonIndex() = menuSize-8-1
    public fun getBackButtonIndex() = menuSize-4-1
    public fun getNextButtonIndex() = menuSize-1
    /**
     * Managing buttons for pages
     *
     * next,prev,back
     */
    fun addManageButtons() {
        if (page >= 1)
            inventory.setItem(
                getPrevButtonIndex(),
                setManageButton(EmpireTemplate.translation.PREV_PAGE,Material.PAPER)
            )

        inventory.setItem(
            getBackButtonIndex(),
            setManageButton(EmpireTemplate.translation.BACK_PAGE,Material.PAPER)
        )

        if (page < maxPages)
            inventory.setItem(
                getNextButtonIndex(),
                setManageButton(EmpireTemplate.translation.NEXT_PAGE,Material.PAPER)
            )

    }
}
