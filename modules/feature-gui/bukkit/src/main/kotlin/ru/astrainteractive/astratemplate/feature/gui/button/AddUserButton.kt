package ru.astrainteractive.astratemplate.feature.gui.button

import org.bukkit.Material
import ru.astrainteractive.astralibs.menu.clicker.Click
import ru.astrainteractive.astralibs.menu.slot.InventorySlot
import ru.astrainteractive.astralibs.menu.slot.setDisplayName
import ru.astrainteractive.astralibs.menu.slot.setIndex
import ru.astrainteractive.astralibs.menu.slot.setMaterial
import ru.astrainteractive.astralibs.menu.slot.setOnClickListener
import ru.astrainteractive.astratemplate.feature.gui.button.di.ButtonContext

internal fun ButtonContext.addUser(
    index: Int,
    click: Click
) = InventorySlot.Builder()
    .setIndex(index)
    .setMaterial(Material.EMERALD)
    .setDisplayName(toComponent(translation.menu.menuAddPlayer))
    .setOnClickListener(click)
    .build()
