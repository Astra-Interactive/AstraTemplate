package ru.astrainteractive.astratemplate.gui.bukkit.slot

import org.bukkit.Material
import ru.astrainteractive.astralibs.menu.clicker.Click
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astralibs.menu.slot.setDisplayName
import ru.astrainteractive.astralibs.menu.slot.setIndex
import ru.astrainteractive.astralibs.menu.slot.setMaterial
import ru.astrainteractive.astralibs.menu.slot.setOnClickListener
import ru.astrainteractive.astratemplate.gui.bukkit.context.SlotContext

internal fun SlotContext.backSlot(
    index: Int,
    click: Click
): InventorySlot {
    return InventorySlot.Builder()
        .setIndex(index)
        .setMaterial(Material.BARRIER)
        .setDisplayName(translation.menu.back.toComponent(locale))
        .setOnClickListener(click)
        .build()
}
