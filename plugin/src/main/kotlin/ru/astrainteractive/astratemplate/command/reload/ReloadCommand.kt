package ru.astrainteractive.astratemplate.command.reload

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.Command

interface ReloadCommand : Command<ReloadCommand.Result, ReloadCommand.Input> {
    sealed interface Result {
        data object NoPermission : Result
        class Success(val sender: CommandSender) : Result
    }

    class Input(val sender: CommandSender)
}
