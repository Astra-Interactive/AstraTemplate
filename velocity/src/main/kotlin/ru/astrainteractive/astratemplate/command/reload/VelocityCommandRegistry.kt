package ru.astrainteractive.astratemplate.command.reload

import com.velocitypowered.api.command.SimpleCommand
import ru.astrainteractive.astralibs.command.api.command.Command
import ru.astrainteractive.astralibs.command.api.registry.CommandRegistry
import ru.astrainteractive.astratemplate.command.api.VelocityCommandContext
import ru.astrainteractive.astratemplate.command.api.VelocityCommandRegistryContext

object VelocityCommandRegistry : CommandRegistry<VelocityCommandRegistryContext, Command<VelocityCommandContext>> {
    override fun register(command: Command<VelocityCommandContext>, registryContext: VelocityCommandRegistryContext) {
        val commandMeta = registryContext.proxyServer.commandManager
            .metaBuilder(command.alias)
            .aliases(command.alias)
            .plugin(registryContext.plugin)
            .build()
        val velocityCommand = SimpleCommand {
            val commandContext = VelocityCommandContext(
                alias = it.alias(),
                source = it.source(),
                arguments = it.arguments()
            )
            command.dispatch(commandContext)
        }
        registryContext.proxyServer.commandManager.register(commandMeta, velocityCommand)
    }
}
