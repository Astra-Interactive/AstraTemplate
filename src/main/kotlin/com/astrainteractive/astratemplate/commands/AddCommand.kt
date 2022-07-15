package com.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astralibs.commands.AstraDSLCommand
import com.astrainteractive.astralibs.utils.withEntry
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack


fun CommandManager.addCommandCompleter() = AstraDSLCommand.onTabComplete("add") {
    onType(0) {
        return@onTabComplete Bukkit.getOnlinePlayers().map { it.name }.withEntry(it)
    }
    onType(1) {
        return@onTabComplete Material.values().map { it.name }.withEntry(it)
    }
    onType(2) {
        return@onTabComplete IntRange(1, 64).map { it.toString() }.withEntry(it)
    }
    return@onTabComplete listOf()
}

/**
 * Add {PLAYER} {ITEM} [AMOUNT]
 */
fun CommandManager.addCommand() = AstraDSLCommand.command("add") {
    if (!sentByPlayer) {
        sender.sendMessage("Sender should be player")
        return@command
    }
    val player = player() {
        onEmptyArgument {
            println("onEmptyArgument")
        }
        onWrongArgument {
            println("onWrongArgument")
        }
        onResultNotFound {
            println("onResultNotFound")
        }
        onFailure {
            println("onFailure")
        }
    } ?: return@command
    val item = item() {
        sender.sendMessage("Item not found")
    } ?: return@command
    val amount = getArgumentOrNull(2)?.toIntOrNull() ?: 1
    player.inventory.addItem(item.apply
    { setAmount(amount) })
}


private fun AstraDSLCommand.DSLCommandBuilder.player(block: AstraDSLCommand.ArgumentResult.() -> Unit): Player? {
    val argument = this.getArgumentOrNull(0) ?: run {
        block(AstraDSLCommand.ArgumentResult(AstraDSLCommand.ArgumentStatus.Empty))
        return null
    }
    return Bukkit.getPlayer(argument) ?: run {
        block(AstraDSLCommand.ArgumentResult(AstraDSLCommand.ArgumentStatus.ResultNotFound))
        null
    }
}

private fun AstraDSLCommand.DSLCommandBuilder.item(onNotFound: () -> Unit): ItemStack? {
    val argument = getArgumentOrNull(1) ?: run {
        onNotFound()
        return null
    }
    val material = Material.matchMaterial(argument) ?: run {
        onNotFound()
        return null
    }
    return ItemStack(material)
}
