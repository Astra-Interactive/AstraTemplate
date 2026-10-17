package ru.astrainteractive.astratemplate.gui.bukkit.sample.util

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import kotlin.random.Random

internal fun Random.nextChatColor(): ChatColor = ChatColor.entries[nextInt(ChatColor.entries.size)]

internal fun Random.nextItemStack(): ItemStack = ItemStack(Material.entries[nextInt(Material.entries.size)])

internal fun Random.nextItemStacks(count: Int): List<ItemStack> = List(count) { nextItemStack() }
