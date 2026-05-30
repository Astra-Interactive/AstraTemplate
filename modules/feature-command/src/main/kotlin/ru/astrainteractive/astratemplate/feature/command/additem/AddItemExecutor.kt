package ru.astrainteractive.astratemplate.feature.command.additem

import net.kyori.adventure.text.Component

internal class AddItemExecutor {
    fun execute(input: AddItemCommand.Result) {
        input.player.sendMessage(
            Component.text("Added ${input.amount}x ${input.itemName}")
        )
    }
}
