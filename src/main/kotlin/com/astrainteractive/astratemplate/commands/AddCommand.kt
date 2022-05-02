package com.astrainteractive.astratemplate.commands

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

private val addCommand = AstraDSLCommand.command("add") {
    AstraDSLCommand.dslCommand(it) {
        if (!sentByPlayer) {
            sender.sendMessage("Sender should be player")
            return@dslCommand
        }
        val player = player() ?: run {
            sender.sendMessage("Player not found")
            return@dslCommand
        }
        val item = item() ?: run {
            sender.sendMessage("Item not found")
            return@dslCommand
        }
        val amount = getArgumentOrNull(3)?.toIntOrNull() ?: 1
        player.inventory.addItem(item.apply { setAmount(amount) })
    }
}

fun AstraDSLCommand.DSLCommandBuilder.player(): Player? {
    return Bukkit.getPlayer(this.getArgumentOrNull(1) ?: return null)
}

fun AstraDSLCommand.DSLCommandBuilder.item(): ItemStack? {
    val material = Material.matchMaterial(this.getArgumentOrNull(2) ?: return null) ?: return null
    return ItemStack(material)
}
