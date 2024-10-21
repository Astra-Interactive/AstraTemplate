package ru.astrainteractive.astratemplate.command.reload

import com.velocitypowered.api.command.CommandSource
import net.kyori.adventure.text.Component
import ru.astrainteractive.astralibs.command.api.error.ErrorHandler
import ru.astrainteractive.astralibs.command.api.exception.CommandException
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.CommandParser
import ru.astrainteractive.astratemplate.command.api.VelocityCommandContext
import ru.astrainteractive.astratemplate.command.api.VelocityCommandRegistryContext
import ru.astrainteractive.astratemplate.command.reload.VelocityCommandRegistry.registerCommand
import ru.astrainteractive.astratemplate.core.PluginPermission

class ReloadCommandRegistry(private val registryContext: VelocityCommandRegistryContext) {

    private interface ReloadCommand {
        sealed interface Result {
            data class Success(val source: CommandSource) : Result
        }

        sealed class Error(message: String) : CommandException(message) {
            data object WrongUsage : Error("WrongUsage")
            data object NoPermission : Error("NoPermission")
        }
    }

    private inner class CommandExecutorImpl : CommandExecutor<ReloadCommand.Result> {
        override fun execute(result: ReloadCommand.Result) {
            if (result !is ReloadCommand.Result.Success) return
            result.source.sendMessage(Component.text("Success"))
            registryContext.plugin.reload()
        }
    }

    private inner class CommandParserImpl : CommandParser<ReloadCommand.Result, VelocityCommandContext> {
        override fun parse(commandContext: VelocityCommandContext): ReloadCommand.Result {
            if (!commandContext.source.hasPermission(PluginPermission.Reload.value)) {
                throw ReloadCommand.Error.NoPermission
            }
            if (commandContext.arguments.isNotEmpty()) {
                throw ReloadCommand.Error.WrongUsage
            }
            return ReloadCommand.Result.Success(commandContext.source)
        }
    }

    private inner class ErrorHandlerImpl : ErrorHandler<VelocityCommandContext> {
        override fun handle(commandContext: VelocityCommandContext, throwable: Throwable) {
            when (throwable) {
                ReloadCommand.Error.NoPermission -> {
                    commandContext.source.sendMessage(Component.text("No permission"))
                }

                ReloadCommand.Error.WrongUsage -> {
                    commandContext.source.sendMessage(Component.text("Wrong usage"))
                }
            }
        }
    }

    fun register() {
        registryContext.registerCommand(
            alias = "reload",
            commandParser = CommandParserImpl(),
            commandExecutor = CommandExecutorImpl(),
            errorHandler = ErrorHandlerImpl()
        )
    }
}
