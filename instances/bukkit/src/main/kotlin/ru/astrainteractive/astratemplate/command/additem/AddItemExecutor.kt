package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.inventory.ItemStack

internal class AddItemExecutor {
    fun execute(input: AddItemCommand.Result) {
        val itemStack = ItemStack(input.item, input.amount)
        input.player.inventory.addItem(itemStack)
    }
}
