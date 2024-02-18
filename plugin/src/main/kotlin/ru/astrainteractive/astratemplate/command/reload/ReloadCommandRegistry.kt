package ru.astrainteractive.astratemplate.command.reload

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.command.BukkitCommand
import ru.astrainteractive.astralibs.command.api.command.Command
import ru.astrainteractive.astralibs.command.api.commandfactory.BukkitCommandFactory
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.BukkitCommandParser
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistry
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistryContext
import ru.astrainteractive.astralibs.command.api.sideeffect.BukkitCommandSideEffect
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.command.reload.di.ReloadCommandDependencies
import ru.astrainteractive.astratemplate.core.PluginPermission

class ReloadCommandRegistry(
    dependencies: ReloadCommandDependencies
) : ReloadCommandDependencies by dependencies {
    interface ReloadCommand : BukkitCommand {
        sealed interface Result {
            data object NoPermission : Result
            class Success(val sender: CommandSender) : Result
        }
    }

    private inner class CommandParserImpl : BukkitCommandParser<ReloadCommand.Result> {
        override fun parse(commandContext: BukkitCommandContext): ReloadCommand.Result {
            val hasPermission = commandContext.sender.toPermissible().hasPermission(PluginPermission.Damage)
            if (!hasPermission) return ReloadCommand.Result.NoPermission
            return ReloadCommand.Result.Success(commandContext.sender)
        }
    }

    private inner class CommandSideEffectImpl : BukkitCommandSideEffect<ReloadCommand.Result> {
        override fun handle(commandContext: BukkitCommandContext, result: ReloadCommand.Result) {
            when (result) {
                ReloadCommand.Result.NoPermission -> with(kyoriComponentSerializer) {
                    commandContext.sender.sendMessage(translation.general.noPermission.let(::toComponent))
                }

                is ReloadCommand.Result.Success -> Unit
            }
        }
    }

    private inner class CommandExecutorImpl : CommandExecutor<ReloadCommand.Result> {
        override fun execute(input: ReloadCommand.Result) {
            if (input !is ReloadCommand.Result.Success) return
            with(kyoriComponentSerializer) {
                input.sender.sendMessage(translation.general.reload.let(::toComponent))
                (plugin as AstraTemplate).reloadPlugin()
                input.sender.sendMessage(translation.general.reloadComplete.let(::toComponent))
            }
        }
    }

    fun register() {
        val command = BukkitCommandFactory.create(
            alias = "reload",
            commandExecutor = CommandExecutorImpl(),
            commandSideEffect = CommandSideEffectImpl(),
            commandParser = CommandParserImpl(),
            mapper = Command.Mapper.NoOp()
        )
        BukkitCommandRegistry.register(
            command = command,
            registryContext = BukkitCommandRegistryContext(plugin)
        )
    }
}
