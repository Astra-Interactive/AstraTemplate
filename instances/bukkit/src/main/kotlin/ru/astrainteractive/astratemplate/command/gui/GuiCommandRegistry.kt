package ru.astrainteractive.astratemplate.command.gui

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.util.PluginExt.setCommandExecutor
import ru.astrainteractive.astratemplate.command.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.gui.di.GuiCommandDependencies
import ru.astrainteractive.astratemplate.gui.router.Router

internal class GuiCommandRegistry(
    dependencies: GuiCommandDependencies
) : GuiCommandDependencies by dependencies {

    fun register() {
        plugin.setCommandExecutor(
            alias = "atempgui",
            commandParser = commandParser@{ commandContext ->
                val player = commandContext.sender as? Player ?: throw GuiCommand.Error.NotPlayer
                val route = Router.Route.Sample
                GuiCommand.Result(player, route)
            },
            commandExecutor = { result ->
                router.open(result.player, result.route)
            },
            errorHandler = DefaultErrorHandler(
                translationKrate = translationKrate,
                kyoriKrate = kyoriKrate
            )
        )
    }
}
