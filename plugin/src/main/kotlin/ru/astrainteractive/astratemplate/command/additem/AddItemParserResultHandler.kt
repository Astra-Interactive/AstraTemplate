package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.CommandParser

internal class AddItemParserResultHandler : CommandParser.ResultHandler<AddItemCommandParser.Result> {
    override fun handle(commandSender: CommandSender, result: AddItemCommandParser.Result) {
        when (result) {
            AddItemCommandParser.Result.ItemNotfound -> {
                commandSender.sendMessage("Item not found")
            }

            AddItemCommandParser.Result.PlayerNotExists -> {
                commandSender.sendMessage("Player not exists")
            }

            AddItemCommandParser.Result.SenderNotPlayer -> {
                commandSender.sendMessage("Sender should be player")
            }

            AddItemCommandParser.Result.NoEx -> Unit
            is AddItemCommandParser.Result.Success -> Unit
        }
    }
}
