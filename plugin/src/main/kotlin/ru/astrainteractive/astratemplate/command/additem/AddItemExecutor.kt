package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ru.astrainteractive.astralibs.command.api.CommandExecutor

internal class AddItemExecutor : CommandExecutor<AddItemExecutor.Input> {
    class Input(
        val player: Player,
        val amount: Int,
        val item: Material
    )

    override fun execute(input: Input) {
        val itemStack = ItemStack(input.item, input.amount)
        input.player.inventory.addItem(itemStack)
    }
}
