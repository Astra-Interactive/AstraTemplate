package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor

internal class AddItemExecutor : CommandExecutor<AddItemCommand.Result> {
    override fun execute(input: AddItemCommand.Result) {
        val itemStack = ItemStack(input.item, input.amount)
        input.player.inventory.addItem(itemStack)
    }
}
