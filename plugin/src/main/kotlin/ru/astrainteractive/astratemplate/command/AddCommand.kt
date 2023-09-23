package ru.astrainteractive.astratemplate.command

import CommandManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.util.withEntry

fun CommandManager.addCommandCompleter() = plugin.registerTabCompleter("add") {
    when (args.size) {
        2 -> Material.values().map { it.name }.withEntry(args.last())
        3 -> IntRange(1, 64).map { it.toString() }.withEntry(args.last())
        else -> Bukkit.getOnlinePlayers().map { it.name }.withEntry(args.last())
    }
}

/**
 * Add {PLAYER} {ITEM} [AMOUNT]
 */
fun CommandManager.addCommand() = plugin.registerCommand("add") {
    if (sender !is Player) {
        sender.sendMessage("Sender should be player")
        return@registerCommand
    }
    val player = argument(0) {
        it?.let(Bukkit::getPlayer)
    }.onFailure { sender.sendMessage("Player not exists") }.successOrNull()?.value ?: return@registerCommand

    val amount = argument(2) { it?.toIntOrNull() ?: 1 }.successOrNull()?.value!!

    val item = argument(1) {
        val material = it?.let(Material::getMaterial)
        material?.let { ItemStack(it, amount) }
    }.onFailure { sender.sendMessage("Item not found") }.successOrNull()?.value ?: return@registerCommand

    player.inventory.addItem(item)
}
