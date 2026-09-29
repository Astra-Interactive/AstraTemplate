package ru.astrainteractive.astratemplate.feature.command.gui

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.feature.gui.router.Router

internal class GuiLiteralArgumentBuilder(
    private val multiplatformCommand: MultiplatformCommand,
    private val router: Router,
    private val errorHandler: DefaultErrorHandler
) {

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
