package ru.astrainteractive.astratemplate.feature.command.gui

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.feature.gui.router.Router
import ru.astrainteractive.klibs.kstorage.api.CachedKrate

internal class GuiLiteralArgumentBuilder(
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val multiplatformCommand: MultiplatformCommand,
    private val router: Router,
    private val errorHandler: DefaultErrorHandler
) : KyoriComponentSerializer by kyoriKrate.unwrap() {

    fun create(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("atempgui") {
                runs(errorHandler::handle) { ctx ->
                    val player = ctx.requirePlayer()
                    router.open(player, Router.Route.Sample)
                }
            }
        }
    }
}
