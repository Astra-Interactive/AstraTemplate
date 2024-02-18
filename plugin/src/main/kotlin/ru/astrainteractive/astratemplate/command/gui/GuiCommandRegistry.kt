package ru.astrainteractive.astratemplate.command.gui

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.command.Command
import ru.astrainteractive.astralibs.command.api.commandfactory.BukkitCommandFactory
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistry
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistryContext.Companion.toCommandRegistryContext
import ru.astrainteractive.astratemplate.command.gui.di.GuiCommandDependencies
import ru.astrainteractive.astratemplate.gui.router.Router

class GuiCommandRegistry(
    dependencies: GuiCommandDependencies
) : GuiCommandDependencies by dependencies {

    fun register() {
        val command = BukkitCommandFactory.create(
            alias = "atempgui",
            commandExecutor = { result ->
                when (result) {
                    GuiCommand.Output.NotPlayer -> Unit
                    is GuiCommand.Output.Route -> {
                        router.open(result.player, result.route)
                    }
                }
            },
            commandSideEffect = commandSideEffect@{ commandContext, result ->
                when (result) {
                    GuiCommand.Output.NotPlayer -> with(kyoriComponentSerializer) {
                        commandContext.sender.sendMessage(translation.general.notPlayer.let(::toComponent))
                    }

                    is GuiCommand.Output.Route -> Unit
                }
            },
            commandParser = commandParser@{ commandContext ->
                val player = commandContext.sender as? Player ?: return@commandParser GuiCommand.Output.NotPlayer
                val route = Router.Route.Sample
                GuiCommand.Output.Route(player, route)
            },
            mapper = Command.Mapper.NoOp()
        )
        BukkitCommandRegistry.register(
            command = command,
            registryContext = plugin.toCommandRegistryContext()
        )
    }
}
