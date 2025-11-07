package ru.astrainteractive.astratemplate.command.reload

import com.velocitypowered.api.command.CommandSource
import com.velocitypowered.api.command.SimpleCommand
import net.kyori.adventure.text.Component
import ru.astrainteractive.astralibs.command.api.exception.CommandException
import ru.astrainteractive.astratemplate.command.api.VelocityCommandRegistryContext
import ru.astrainteractive.astratemplate.command.api.registerCommand
import ru.astrainteractive.astratemplate.core.plugin.PluginPermission

class ReloadCommandRegistry(private val registryContext: VelocityCommandRegistryContext) {

    private interface ReloadCommand {
        sealed interface Result {
            data class Success(val source: CommandSource) : Result
        }

        sealed class ReloadException(message: String) : CommandException(message) {
            class WrongUsage : ReloadException("WrongUsage")
            class NoPermission : ReloadException("NoPermission")
        }
    }

    private fun execute(result: ReloadCommand.Result) {
        if (result !is ReloadCommand.Result.Success) return
        result.source.sendMessage(Component.text("Success"))
        registryContext.plugin.reload()
    }

    private fun parse(ctx: SimpleCommand.Invocation): Result<ReloadCommand.Result.Success> {
        return runCatching {
            if (!ctx.source().hasPermission(PluginPermission.Reload.value)) {
                throw ReloadCommand.ReloadException.NoPermission()
            }
            if (ctx.arguments().isNotEmpty()) {
                throw ReloadCommand.ReloadException.WrongUsage()
            }
            ReloadCommand.Result.Success(ctx.source())
        }
    }

    private fun handle(ctx: SimpleCommand.Invocation, throwable: Throwable) {
        when (throwable) {
            is ReloadCommand.ReloadException.NoPermission -> {
                ctx.source().sendMessage(Component.text("No permission"))
            }

            is ReloadCommand.ReloadException.WrongUsage -> {
                ctx.source().sendMessage(Component.text("Wrong usage"))
            }
        }
    }

    fun register() {
        registryContext.registerCommand(
            alias = "reload",
            parse = ::parse,
            onSuccess = ::execute,
            onFailure = ::handle
        )
    }
}
