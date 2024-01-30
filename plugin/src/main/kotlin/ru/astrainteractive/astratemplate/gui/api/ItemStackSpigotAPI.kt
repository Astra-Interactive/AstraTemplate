package ru.astrainteractive.astratemplate.gui.api

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

object ItemStackSpigotAPI {
    fun randomItemStack(): ItemStack {
        val values = Material.entries
        val size = values.size
        val i = Random.nextInt(size)
        return ItemStack(values[i])
    }
    fun randomItemStackList(size: Int = 70) = IntRange(0, size).map { randomItemStack() }
}
