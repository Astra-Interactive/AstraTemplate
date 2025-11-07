package ru.astrainteractive.astratemplate.command.damage

import com.mojang.brigadier.arguments.DoubleArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.tree.LiteralCommandNode
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.command.api.exception.NoPlayerException
import ru.astrainteractive.astralibs.command.api.util.argument
import ru.astrainteractive.astralibs.command.api.util.command
import ru.astrainteractive.astralibs.command.api.util.requireArgument
import ru.astrainteractive.astralibs.command.api.util.requirePermission
import ru.astrainteractive.astralibs.command.api.util.runs
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.kyori.unwrap
import ru.astrainteractive.astratemplate.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.core.plugin.PluginPermission
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.CachedKrate
import ru.astrainteractive.klibs.kstorage.util.getValue

internal class DamageCommandRegistry(
    translationKrate: CachedKrate<PluginTranslation>,
    kyoriKrate: CachedKrate<KyoriComponentSerializer>,
    private val errorHandler: DefaultErrorHandler
) : KyoriComponentSerializer by kyoriKrate.unwrap() {
    private val translation by translationKrate

    fun createNode(): LiteralCommandNode<CommandSourceStack> {
        return command("adamage") {
            argument("player", StringArgumentType.word()) { playerArg ->
                runs(errorHandler::handle) { ctx ->
                    ctx.requirePermission(PluginPermission.Damage)

                    val playerName = ctx.requireArgument(playerArg)
                    val player = Bukkit.getPlayerExact(playerName) ?: throw NoPlayerException(playerName)
                    player.sendMessage(translation.custom.damaged(ctx.source.sender.name).component)
                    player.damage(0.0)
                }
                argument("damage", DoubleArgumentType.doubleArg(0.0)) { damageArg ->
                    runs(errorHandler::handle) { ctx ->
                        ctx.requirePermission(PluginPermission.Damage)

                        val playerName = ctx.requireArgument(playerArg)
                        val player = Bukkit.getPlayerExact(playerName) ?: throw NoPlayerException(playerName)
                        val damage = ctx.requireArgument(damageArg)
                        player.sendMessage(translation.custom.damaged(ctx.source.sender.name).component)
                        player.damage(damage)
                    }
                }
            }
        }.build()
    }
}
