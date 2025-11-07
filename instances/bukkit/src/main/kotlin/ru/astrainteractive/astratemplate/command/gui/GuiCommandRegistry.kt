package ru.astrainteractive.astratemplate.command.gui

import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import ru.astrainteractive.astralibs.command.api.util.command
import ru.astrainteractive.astralibs.command.api.util.requirePlayer
import ru.astrainteractive.astralibs.command.api.util.runs
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kstorage.api.CachedKrate

internal class GuiCommandRegistry(
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val router: Router,
    private val errorHandler: DefaultErrorHandler
) : KyoriComponentSerializer by kyoriKrate.unwrap() {

    fun createNode(): LiteralCommandNode<CommandSourceStack> {
        return command("atempgui") {
            runs(errorHandler::handle) { ctx ->
                val player = ctx.requirePlayer()
                val route = Router.Route.Sample
                router.open(player, route)
            }
        }.build()
    }
}
