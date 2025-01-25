package ru.astrainteractive.astratemplate.command.damage

import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.exception.NoPermissionException
import ru.astrainteractive.astralibs.command.api.exception.NoPlayerException
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.CommandParser
import ru.astrainteractive.astralibs.command.api.util.PluginExt.setCommandExecutor
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.astratemplate.command.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.damage.di.DamageCommandDependencies
import ru.astrainteractive.astratemplate.core.PluginPermission

internal class DamageCommandRegistry(
    dependencies: DamageCommandDependencies
) : DamageCommandDependencies by dependencies {
    private val alias = "adamage"

    private inner class CommandParserImpl : CommandParser<DamageCommand.Result, BukkitCommandContext> {
        override fun parse(commandContext: BukkitCommandContext): DamageCommand.Result {
            val hasPermission = commandContext.sender.toPermissible().hasPermission(PluginPermission.Damage)
            if (!hasPermission) throw NoPermissionException(PluginPermission.Damage)

            val playerString = commandContext.args.getOrNull(0)
            val player = playerString
                ?.let(Bukkit::getPlayerExact)
                ?: throw NoPlayerException(playerString.orEmpty())

            val damage = commandContext.args.getOrNull(1)
                ?.toDoubleOrNull()
                ?: 0.0

            return DamageCommand.Result(
                player = player,
                damage = damage,
                damagerName = commandContext.sender.name
            )
        }
    }

    private inner class CommandExecutorImpl : CommandExecutor<DamageCommand.Result> {
        override fun execute(input: DamageCommand.Result) {
            with(kyori) {
                input.player.sendMessage(translation.custom.damaged(input.damagerName).let(::toComponent))
            }
            input.player.damage(input.damage)
        }
    }

    private fun tabCompleter() = plugin.getCommand(alias)?.setTabCompleter { sender, command, label, args ->
        when (args.size) {
            0 -> listOf("adamage")
            1 -> Bukkit.getOnlinePlayers().map { it.name }
            2 -> listOf(translation.custom.damageHint.raw)
            else -> Bukkit.getOnlinePlayers().map { it.name }
        }
    }

    fun register() {
        tabCompleter()
        plugin.setCommandExecutor(
            alias = alias,
            commandParser = CommandParserImpl(),
            commandExecutor = CommandExecutorImpl(),
            errorHandler = DefaultErrorHandler(
                translationKrate = translationKrate,
                kyoriKrate = kyoriKrate
            )
        )
    }
}
