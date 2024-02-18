package ru.astrainteractive.astratemplate.command.reload

import net.kyori.adventure.text.Component
import ru.astrainteractive.astralibs.command.api.command.Command
import ru.astrainteractive.astralibs.command.api.commandfactory.DefaultCommandFactory
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.CommandParser
import ru.astrainteractive.astralibs.command.api.sideeffect.CommandSideEffect
import ru.astrainteractive.astratemplate.command.api.VelocityCommandContext
import ru.astrainteractive.astratemplate.command.api.VelocityCommandRegistryContext
import ru.astrainteractive.astratemplate.core.PluginPermission

class ReloadCommandRegistry(private val registryContext: VelocityCommandRegistryContext) {

    private interface ReloadCommand : Command<VelocityCommandContext> {
        sealed interface Result {
            data object WrongUsage : Result
            data object NoPermission : Result
            data object Success : Result
        }
    }

    private inner class CommandExecutorImpl : CommandExecutor<ReloadCommand.Result> {
        override fun execute(input: ReloadCommand.Result) {
            if (input !is ReloadCommand.Result.Success) return
            registryContext.plugin.reload()
        }
    }

    private inner class CommandParserImpl : CommandParser<ReloadCommand.Result, VelocityCommandContext> {
        override fun parse(commandContext: VelocityCommandContext): ReloadCommand.Result {
            if (!commandContext.source.hasPermission(PluginPermission.Reload.value)) {
                return ReloadCommand.Result.NoPermission
            }
            if (commandContext.arguments.isNotEmpty()) {
                return ReloadCommand.Result.WrongUsage
            }
            return ReloadCommand.Result.Success
        }
    }

    private inner class SideEffectImpl : CommandSideEffect<ReloadCommand.Result, VelocityCommandContext> {
        override fun handle(commandContext: VelocityCommandContext, result: ReloadCommand.Result) {
            when (result) {
                ReloadCommand.Result.NoPermission -> {
                    commandContext.source.sendMessage(Component.text("No permission"))
                }

                ReloadCommand.Result.Success -> {
                    commandContext.source.sendMessage(Component.text("Success"))
                }

                ReloadCommand.Result.WrongUsage -> {
                    commandContext.source.sendMessage(Component.text("Wrong usage"))
                }
            }
        }
    }

    fun register() {
        val command = DefaultCommandFactory<VelocityCommandContext>().create(
            alias = "reload",
            commandParser = CommandParserImpl(),
            commandExecutor = CommandExecutorImpl(),
            commandSideEffect = SideEffectImpl(),
            mapper = Command.Mapper.NoOp()
        )
        VelocityCommandRegistry.register(command, registryContext)
    }
}
