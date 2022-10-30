package ru.astrainteractive.astratemplate.commands

import CommandManager
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.utils.registerCommand
import ru.astrainteractive.astralibs.utils.registerTabCompleter
import ru.astrainteractive.astralibs.utils.withEntry


fun CommandManager.addCommandCompleter() = AstraLibs.registerTabCompleter("add") { sender, args ->
    if (args.size == 0)
        return@registerTabCompleter Bukkit.getOnlinePlayers().map { it.name }.withEntry(args.last())

    if (args.size == 1)
        return@registerTabCompleter Material.values().map { it.name }.withEntry(args.last())

    if (args.size == 2)
        return@registerTabCompleter IntRange(1, 64).map { it.toString() }.withEntry(args.last())

    return@registerTabCompleter listOf()
}

/**
 * Add {PLAYER} {ITEM} [AMOUNT]
 */
fun CommandManager.addCommand() = AstraLibs.registerCommand("add") { sender, args ->
    if (sender !is Player) {
        sender.sendMessage("Sender should be player")
        return@registerCommand
    }

    val player = Bukkit.getPlayer(args.getOrNull(0) ?: "") ?: run {
        sender.sendMessage("Plater not exists")
        return@registerCommand
    }
    val amount = args.getOrNull(2)?.toIntOrNull() ?: 1
    val item = args.getOrNull(1)?.let(Material::getMaterial)?.let { ItemStack(it, amount) } ?: run {
        sender.sendMessage("Item not found")
        return@registerCommand
    }
    player.inventory.addItem(item)
}
