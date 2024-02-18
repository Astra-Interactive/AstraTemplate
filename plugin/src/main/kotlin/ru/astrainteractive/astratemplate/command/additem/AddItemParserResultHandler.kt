package ru.astrainteractive.astratemplate.command.additem

import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.sideeffect.BukkitCommandSideEffect

internal class AddItemParserResultHandler : BukkitCommandSideEffect<AddItemCommand.Result> {
    override fun handle(commandContext: BukkitCommandContext, result: AddItemCommand.Result) {
        when (result) {
            AddItemCommand.Result.ItemNotfound -> {
                commandContext.sender.sendMessage("Item not found")
            }

            AddItemCommand.Result.PlayerNotExists -> {
                commandContext.sender.sendMessage("Player not exists")
            }

            AddItemCommand.Result.SenderNotPlayer -> {
                commandContext.sender.sendMessage("Sender should be player")
            }

            AddItemCommand.Result.NoEx -> Unit
            is AddItemCommand.Result.Success -> Unit
        }
    }
}
