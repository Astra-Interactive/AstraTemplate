package com.astrainteractive.astratemplate.gui

import org.bukkit.event.inventory.InventoryClickEvent
import ru.astrainteractive.astralibs.menu.IInventoryButton

interface IClickableInventoryButton : IInventoryButton {
    fun listen(it: InventoryClickEvent) {
        if (it.slot != this.index) return
        onClick()
    }

    fun onClick()
}