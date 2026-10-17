package ru.astrainteractive.astratemplate.gui.bukkit.slot

import org.bukkit.Material
import ru.astrainteractive.astralibs.menu.clicker.Click
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astralibs.menu.slot.setDisplayName
import ru.astrainteractive.astralibs.menu.slot.setIndex
import ru.astrainteractive.astralibs.menu.slot.setMaterial
import ru.astrainteractive.astralibs.menu.slot.setOnClickListener
import ru.astrainteractive.astratemplate.gui.bukkit.context.SlotContext

internal fun SlotContext.nextPageSlot(
    index: Int,
    click: Click
) = InventorySlot.Builder()
    .setIndex(index)
    .setMaterial(Material.PAPER)
    .setDisplayName(translation.menu.nextPage.toComponent(locale))
    .setOnClickListener(click)
    .build()
