package ru.astrainteractive.astratemplate.command.reload

import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.CommandParser
import ru.astrainteractive.astralibs.command.api.DefaultCommandFactory
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.command.reload.di.ReloadCommandDependencies
import ru.astrainteractive.astratemplate.core.PluginPermission
import ru.astrainteractive.klibs.kdi.Factory

class ReloadCommandFactory(
    dependencies: ReloadCommandDependencies
) : Factory<ReloadCommand>, ReloadCommandDependencies by dependencies {
    private val alias = "atempreload"

    private val commandParser = CommandParser { args, sender ->
        val hasPermission = sender.toPermissible().hasPermission(PluginPermission.Damage)
        if (!hasPermission) return@CommandParser ReloadCommand.Result.NoPermission
        ReloadCommand.Result.Success(sender)
    }

    inner class ReloadCommandImpl :
        ReloadCommand,
        Command<ReloadCommand.Result, ReloadCommand.Input> by DefaultCommandFactory.create(
            alias = alias,
            commandParser = commandParser,
            commandExecutor = {
                with(kyoriComponentSerializer) {
                    it.sender.sendMessage(translation.general.reload.let(::toComponent))
                    (plugin as AstraTemplate).reloadPlugin()
                    it.sender.sendMessage(translation.general.reloadComplete.let(::toComponent))
                }
            },
            resultHandler = { commandSender, result ->
                when (result) {
                    ReloadCommand.Result.NoPermission -> with(kyoriComponentSerializer) {
                        commandSender.sendMessage(translation.general.noPermission.let(::toComponent))
                    }

                    is ReloadCommand.Result.Success -> Unit
                }
            },
            mapper = {
                (it as? ReloadCommand.Result.Success)?.sender?.let(ReloadCommand::Input)
            }
        )

    override fun create(): ReloadCommand {
        return ReloadCommandImpl().also {
            it.register(plugin)
        }
    }
}
