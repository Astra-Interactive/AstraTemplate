package ru.astrainteractive.astratemplate.command.gui

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.DefaultCommandFactory
import ru.astrainteractive.astratemplate.command.gui.di.GuiCommandDependencies
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kdi.Factory

class GuiCommandFactory(
    dependencies: GuiCommandDependencies
) : Factory<GuiCommand>, GuiCommandDependencies by dependencies {

    override fun create(): GuiCommand {
        return GuiCommandImpl().also {
            it.register(plugin)
        }
    }

    private inner class GuiCommandImpl :
        GuiCommand,
        Command<GuiCommand.Output, GuiCommand.Output> by DefaultCommandFactory.create(
            alias = "atempgui",
            commandExecutor = { result ->
                when (result) {
                    GuiCommand.Output.NotPlayer -> Unit
                    is GuiCommand.Output.Route -> {
                        router.open(result.player, result.route)
                    }
                }
            },
            resultHandler = { sender, result ->
                when (result) {
                    GuiCommand.Output.NotPlayer -> with(kyoriComponentSerializer) {
                        sender.sendMessage(translation.general.notPlayer.let(::toComponent))
                    }

                    is GuiCommand.Output.Route -> Unit
                }
            },
            commandParser = { args, sender ->
                val player = sender as? Player ?: return@create GuiCommand.Output.NotPlayer
                val route = Router.Route.Sample
                GuiCommand.Output.Route(player, route)
            },
            mapper = Command.Mapper.NoOp()
        )
}
