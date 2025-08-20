package ru.astrainteractive.astratemplate.command.gui

import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.error.ErrorHandler
import ru.astrainteractive.astralibs.command.api.util.PluginExt.setCommandExecutor
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kstorage.api.CachedKrate

internal class GuiCommandRegistry(
    private val plugin: JavaPlugin,
    private val errorHandler: ErrorHandler<BukkitCommandContext>,
    private val router: Router,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>
) : KyoriComponentSerializer by kyoriKrate.unwrap() {

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
            errorHandler = errorHandler
        )
    }
}
