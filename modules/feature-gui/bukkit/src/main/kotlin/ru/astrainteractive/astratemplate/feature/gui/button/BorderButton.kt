package ru.astrainteractive.astratemplate.feature.gui.button

import org.bukkit.Material
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astralibs.menu.slot.setDisplayName
import ru.astrainteractive.astralibs.menu.slot.setIndex
import ru.astrainteractive.astralibs.menu.slot.setMaterial
import ru.astrainteractive.astratemplate.feature.gui.button.di.ButtonContext

internal fun ButtonContext.border(index: Int) = InventorySlot.Builder()
    .setIndex(index)
    .setMaterial(Material.GRAY_STAINED_GLASS_PANE)
    .setDisplayName(" ")
    .build()
