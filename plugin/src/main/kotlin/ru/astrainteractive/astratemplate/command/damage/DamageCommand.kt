package ru.astrainteractive.astratemplate.command.damage

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.CommandExecutor
import ru.astrainteractive.astralibs.command.api.CommandParser
import ru.astrainteractive.astralibs.command.registerTabCompleter
import ru.astrainteractive.astralibs.command.types.OnlinePlayerArgument
import ru.astrainteractive.astralibs.permission.PermissionManager
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.shared.core.Permissions
import ru.astrainteractive.astratemplate.shared.core.Translation

class DamageCommand(
    private val translation: Translation,
    private val permissionManager: PermissionManager,
    translationContext: BukkitTranslationContext
) : Command, BukkitTranslationContext by translationContext {

    private sealed interface Result {
        data object NoOp : Result
        data object NoPermission : Result
        data object PlayerNotExists : Result
        class Success(val player: Player, val damage: Double, val damagerName: String) : Result
    }

    private class Input(val player: Player, val damage: Double, val damagerName: String)

    private val commandParser = CommandParser.Default("adamage") { args, sender ->
        if (sender is Player) {
            val hasPermission = permissionManager.hasPermission(sender.uniqueId, Permissions.Damage)
            if (!hasPermission) return@Default Result.NoPermission
        }

        val player = args.getOrNull(0)
            ?.let(OnlinePlayerArgument::transform)
            ?: return@Default Result.PlayerNotExists

        val damage = args.getOrNull(1)
            ?.toDoubleOrNull()
            ?: 0.0

        Result.Success(
            player = player,
            damage = damage,
            damagerName = sender.name
        )
    }

    private val resultHandler = CommandParser.ResultHandler<Result> { commandSender, result ->
        when (result) {
            Result.PlayerNotExists -> {
                commandSender.sendMessage(translation.custom.noPlayerName)
            }

            Result.NoPermission -> {
                commandSender.sendMessage(translation.general.noPermission)
            }

            Result.NoOp -> Unit
            is Result.Success -> Unit
        }
    }

    private val commandExecutor = CommandExecutor<Input> {
        it.player.sendMessage(translation.custom.damaged(it.damagerName))
        it.player.damage(it.damage)
    }

    private fun tabCompleter(plugin: JavaPlugin) = plugin.registerTabCompleter(commandParser.alias) {
        when (args.size) {
            0 -> listOf("adamage")
            1 -> Bukkit.getOnlinePlayers().map { it.name }
            2 -> listOf(translation.custom.damageHint.raw)
            else -> Bukkit.getOnlinePlayers().map { it.name }
        }
    }

    override fun register(plugin: JavaPlugin) {
        tabCompleter(plugin)
        Command.registerDefault(
            plugin = plugin,
            commandParser = commandParser,
            commandExecutor = commandExecutor,
            resultHandler = resultHandler,
            transform = {
                (it as? Result.Success)?.let {
                    Input(
                        player = it.player,
                        damage = it.damage,
                        damagerName = it.damagerName
                    )
                }
            }
        )
    }
}
