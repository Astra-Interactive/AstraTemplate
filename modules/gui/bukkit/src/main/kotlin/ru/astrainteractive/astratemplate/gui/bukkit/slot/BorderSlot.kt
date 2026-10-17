package ru.astrainteractive.astratemplate.gui.bukkit.slot

import org.bukkit.Material
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astralibs.menu.slot.setDisplayName
import ru.astrainteractive.astralibs.menu.slot.setIndex
import ru.astrainteractive.astralibs.menu.slot.setMaterial
import ru.astrainteractive.astratemplate.gui.bukkit.context.SlotContext

internal fun SlotContext.borderSlot(index: Int) = InventorySlot.Builder()
    .setIndex(index)
    .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
    .setDisplayName(" ")
    .build()
