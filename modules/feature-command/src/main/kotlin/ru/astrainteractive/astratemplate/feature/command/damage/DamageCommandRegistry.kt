package ru.astrainteractive.astratemplate.feature.command.damage

import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.core.plugin.PluginPermission
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

internal class DamageCommandRegistry(
    translationKrate: CachedKrate<PluginTranslation>,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val registrarContext: CommandRegistrarContext,
    private val multiplatformCommand: MultiplatformCommand,
    private val errorHandler: DefaultErrorHandler
) : KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    private fun createNode(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("adamage") {
                runs(errorHandler::handle) { ctx ->
                    ctx.requirePermission(PluginPermission.Damage)
                    val player = ctx.requirePlayer()
                    player.sendMessage(translation.custom.damaged(player.name).component)
                }
                argument("damage", DoubleArgumentType.doubleArg(0.0)) { damageArg ->
                    runs(errorHandler::handle) { ctx ->
                        ctx.requirePermission(PluginPermission.Damage)
                        val player = ctx.requirePlayer()
                        ctx.requireArgument(damageArg)
                        player.sendMessage(translation.custom.damaged(player.name).component)
                        player.sendMessage(translation.custom.damageHint.component)
                    }
                }
            }
        }
    }

    fun register() {
        registrarContext.registerWhenReady(createNode())
    }
}
