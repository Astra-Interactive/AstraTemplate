package ru.astrainteractive.astratemplate.command.damage

import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.command.api.commandfactory.BukkitCommandFactory
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.executor.CommandExecutor
import ru.astrainteractive.astralibs.command.api.parser.BukkitCommandParser
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistry
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistryContext.Companion.toCommandRegistryContext
import ru.astrainteractive.astralibs.command.api.sideeffect.BukkitCommandSideEffect
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.astratemplate.command.damage.di.DamageCommandDependencies
import ru.astrainteractive.astratemplate.core.PluginPermission

class DamageCommandRegistry(
    dependencies: DamageCommandDependencies
) : DamageCommandDependencies by dependencies {
    private val alias = "adamage"

    private inner class CommandParserImpl : BukkitCommandParser<DamageCommand.Result> {
        override fun parse(commandContext: BukkitCommandContext): DamageCommand.Result {
            val hasPermission = commandContext.sender.toPermissible().hasPermission(PluginPermission.Damage)
            if (!hasPermission) return DamageCommand.Result.NoPermission

            val player = commandContext.args.getOrNull(0)
                ?.let(Bukkit::getPlayerExact)
                ?: return DamageCommand.Result.PlayerNotExists

            val damage = commandContext.args.getOrNull(1)
                ?.toDoubleOrNull()
                ?: 0.0

            return DamageCommand.Result.Success(
                player = player,
                damage = damage,
                damagerName = commandContext.sender.name
            )
        }
    }

    private inner class CommandSideEffectImpl : BukkitCommandSideEffect<DamageCommand.Result> {
        override fun handle(commandContext: BukkitCommandContext, result: DamageCommand.Result) {
            when (result) {
                DamageCommand.Result.PlayerNotExists -> with(kyoriComponentSerializer) {
                    commandContext.sender.sendMessage(translation.custom.noPlayerName.let(::toComponent))
                }

                DamageCommand.Result.NoPermission -> with(kyoriComponentSerializer) {
                    commandContext.sender.sendMessage(translation.general.noPermission.let(::toComponent))
                }

                DamageCommand.Result.NoOp -> Unit
                is DamageCommand.Result.Success -> Unit
            }
        }
    }

    private inner class CommandExecutorImpl : CommandExecutor<DamageCommand.Input> {
        override fun execute(input: DamageCommand.Input) {
            with(kyoriComponentSerializer) {
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
        val command = BukkitCommandFactory.create(
            alias = alias,
            commandParser = CommandParserImpl(),
            commandExecutor = CommandExecutorImpl(),
            commandSideEffect = CommandSideEffectImpl(),
            mapper = {
                (it as? DamageCommand.Result.Success)?.let {
                    DamageCommand.Input(
                        player = it.player,
                        damage = it.damage,
                        damagerName = it.damagerName
                    )
                }
            }
        )
        BukkitCommandRegistry.register(
            command = command,
            registryContext = plugin.toCommandRegistryContext()
        )
    }
}
