package ru.astrainteractive.astratemplate.feature.command.damage

import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astratemplate.core.plugin.PluginPermission
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.api.getValue

internal class DamageLiteralArgumentBuilder(
    translationKrate: CachedKrate<PluginTranslation>,
    private val multiplatformCommand: MultiplatformCommand,
    private val errorHandler: DefaultErrorHandler
) {
    private val translation by translationKrate

    fun create(): LiteralArgumentBuilder<*> {
        return with(multiplatformCommand) {
            command("adamage") {
                runs(errorHandler::handle) { ctx ->
                    ctx.requirePermission(PluginPermission.Damage)
                    val player = ctx.requirePlayer()
                    player.sendMessage(translation.damage.damagedBy(player.name))
                }
                argument("damage", DoubleArgumentType.doubleArg(0.0)) { damageArg ->
                    runs(errorHandler::handle) { ctx ->
                        ctx.requirePermission(PluginPermission.Damage)
                        val player = ctx.requirePlayer()
                        ctx.requireArgument(damageArg)
                        player.sendMessage(translation.damage.damagedBy(player.name))
                        player.sendMessage(translation.damage.hint)
                    }
                }
            }
        }
    }
}
