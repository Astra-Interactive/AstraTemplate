package com.makeevrserg.empiretemplate.menumanager

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryHolder


data class PlayerMenuUtility(val player: Player){
    val previousItems:MutableList<String> = mutableListOf()
}

public abstract class Menu(open var playerMenuUtility: PlayerMenuUtility) :InventoryHolder {

    private lateinit var inventory: Inventory
    abstract var menuName: String
    abstract val slots: Int
    abstract fun handleMenu(e: InventoryClickEvent)
    abstract fun setMenuItems()

    fun open() {
        inventory = Bukkit.createInventory(this, slots, menuName)
        setMenuItems()
        playerMenuUtility.player.openInventory(inventory)
    }

    override fun getInventory() = inventory

}
