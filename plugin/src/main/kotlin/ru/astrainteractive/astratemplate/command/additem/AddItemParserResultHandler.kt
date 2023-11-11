package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.Command

internal class AddItemParserResultHandler : Command.ResultHandler<AddItemCommand.Result> {
    override fun handle(commandSender: CommandSender, result: AddItemCommand.Result) {
        when (result) {
            AddItemCommand.Result.ItemNotfound -> {
                commandSender.sendMessage("Item not found")
            }

            AddItemCommand.Result.PlayerNotExists -> {
                commandSender.sendMessage("Player not exists")
            }

            AddItemCommand.Result.SenderNotPlayer -> {
                commandSender.sendMessage("Sender should be player")
            }

            AddItemCommand.Result.NoEx -> Unit
            is AddItemCommand.Result.Success -> Unit
        }
    }
}
