package ru.astrainteractive.astratemplate.command.reload

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.exception.NoPermissionException
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.CommandParser
import ru.astrainteractive.astralibs.command.api.util.PluginExt.setCommandExecutor
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.command.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.reload.di.ReloadCommandDependencies
import ru.astrainteractive.astratemplate.core.plugin.PluginPermission

internal class ReloadCommandRegistry(
    dependencies: ReloadCommandDependencies
) : ReloadCommandDependencies by dependencies {

    interface ReloadCommand {
        class Result(val sender: CommandSender)
    }

    private inner class CommandParserImpl : CommandParser<ReloadCommand.Result, BukkitCommandContext> {
        override fun parse(ctx: BukkitCommandContext): ReloadCommand.Result {
            val hasPermission = ctx.sender.toPermissible().hasPermission(PluginPermission.Damage)
            if (!hasPermission) throw NoPermissionException(PluginPermission.Damage)
            return ReloadCommand.Result(ctx.sender)
        }
    }

    private inner class CommandExecutorImpl : CommandExecutor<ReloadCommand.Result> {
        override fun execute(input: ReloadCommand.Result) {
            with(kyori) {
                input.sender.sendMessage(translation.general.reload.let(::toComponent))
                (plugin as AstraTemplate).onReload()
                input.sender.sendMessage(translation.general.reloadComplete.let(::toComponent))
            }
        }
    }

    fun register() {
        plugin.setCommandExecutor(
            alias = "atempreload",
            commandParser = CommandParserImpl(),
            commandExecutor = CommandExecutorImpl(),
            errorHandler = DefaultErrorHandler(
                translationKrate = translationKrate,
                kyoriKrate = kyoriKrate
            )
        )
    }
}
