package ru.astrainteractive.astratemplate.command.reload

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.exception.CommandException
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.CommandParser
import ru.astrainteractive.astralibs.command.api.util.PluginExt.registerCommand
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.command.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.reload.di.ReloadCommandDependencies
import ru.astrainteractive.astratemplate.core.PluginPermission

class ReloadCommandRegistry(
    dependencies: ReloadCommandDependencies
) : ReloadCommandDependencies by dependencies {

    interface ReloadCommand {
        class Result(val sender: CommandSender)

        sealed class Error(message: String) : CommandException(message) {
            data object NoPermission : Error("NoPermission")
        }
    }

    private inner class CommandParserImpl : CommandParser<ReloadCommand.Result, BukkitCommandContext> {
        override fun parse(commandContext: BukkitCommandContext): ReloadCommand.Result {
            val hasPermission = commandContext.sender.toPermissible().hasPermission(PluginPermission.Damage)
            if (!hasPermission) throw ReloadCommand.Error.NoPermission
            return ReloadCommand.Result(commandContext.sender)
        }
    }

    private inner class CommandExecutorImpl : CommandExecutor<ReloadCommand.Result> {
        override fun execute(input: ReloadCommand.Result) {
            with(kyoriComponentSerializer) {
                input.sender.sendMessage(translation.general.reload.let(::toComponent))
                (plugin as AstraTemplate).reloadPlugin()
                input.sender.sendMessage(translation.general.reloadComplete.let(::toComponent))
            }
        }
    }

    fun register() {
        plugin.registerCommand(
            alias = "reload",
            commandParser = CommandParserImpl(),
            commandExecutor = CommandExecutorImpl(),
            errorHandler = DefaultErrorHandler()
        )
    }
}
