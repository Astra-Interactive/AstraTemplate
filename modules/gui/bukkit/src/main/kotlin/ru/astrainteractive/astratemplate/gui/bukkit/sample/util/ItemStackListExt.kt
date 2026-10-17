package ru.astrainteractive.astratemplate.gui.bukkit.sample.util

import org.bukkit.ChatColor
import org.bukkit.inventory.ItemStack

internal fun List<ItemStack>.withColoredDisplayName(index: Int, color: ChatColor): List<ItemStack> {
    val item = getOrNull(index)?.clone() ?: return this
    item.editMeta { meta -> meta.setDisplayName(color.toString() + item.type.name) }
    return toMutableList().apply { set(index, item) }
}
