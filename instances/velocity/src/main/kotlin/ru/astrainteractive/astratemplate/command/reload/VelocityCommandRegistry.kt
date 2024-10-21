package ru.astrainteractive.astratemplate.command.reload

import com.velocitypowered.api.command.SimpleCommand
import ru.astrainteractive.astralibs.command.api.error.ErrorHandler
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.CommandParser
import ru.astrainteractive.astratemplate.command.api.VelocityCommandContext
import ru.astrainteractive.astratemplate.command.api.VelocityCommandRegistryContext

object VelocityCommandRegistry {
    fun <T : Any> VelocityCommandRegistryContext.registerCommand(
        alias: String,
        commandParser: CommandParser<T, VelocityCommandContext>,
        commandExecutor: CommandExecutor<T>,
        errorHandler: ErrorHandler<VelocityCommandContext>
    ) {
        val commandMeta = this.proxyServer.commandManager
            .metaBuilder(alias)
            .aliases(alias)
            .plugin(this.plugin)
            .build()
        val velocityCommand = SimpleCommand { invocation ->
            val commandContext = VelocityCommandContext(
                alias = invocation.alias(),
                source = invocation.source(),
                arguments = invocation.arguments()
            )
            try {
                val result = commandParser.parse(commandContext)
                commandExecutor.execute(result)
            } catch (throwable: Throwable) {
                errorHandler.handle(commandContext, throwable)
            }
        }
        proxyServer.commandManager.register(commandMeta, velocityCommand)
    }
}
