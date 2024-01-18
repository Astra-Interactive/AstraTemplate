package ru.astrainteractive.astratemplate.command.damage

import org.bukkit.Bukkit
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.CommandExecutor
import ru.astrainteractive.astralibs.command.api.CommandParser
import ru.astrainteractive.astralibs.command.api.DefaultCommandFactory
import ru.astrainteractive.astralibs.permission.BukkitPermissibleExt.toPermissible
import ru.astrainteractive.astratemplate.command.damage.di.DamageCommandDependencies
import ru.astrainteractive.astratemplate.core.PluginPermission
import ru.astrainteractive.klibs.kdi.Factory

class DamageCommandFactory(
    dependencies: DamageCommandDependencies
) : Factory<DamageCommand>, DamageCommandDependencies by dependencies {
    private val alias = "adamage"

    private val commandParser = CommandParser { args, sender ->

        val hasPermission = sender.toPermissible().hasPermission(PluginPermission.Damage)
        if (!hasPermission) return@CommandParser DamageCommand.Result.NoPermission

        val player = args.getOrNull(0)
            ?.let(Bukkit::getPlayerExact)
            ?: return@CommandParser DamageCommand.Result.PlayerNotExists

        val damage = args.getOrNull(1)
            ?.toDoubleOrNull()
            ?: 0.0

        DamageCommand.Result.Success(
            player = player,
            damage = damage,
            damagerName = sender.name
        )
    }

    private val resultHandler = Command.ResultHandler<DamageCommand.Result> { commandSender, result ->
        when (result) {
            DamageCommand.Result.PlayerNotExists -> with(kyoriComponentSerializer) {
                commandSender.sendMessage(translation.custom.noPlayerName.let(::toComponent))
            }

            DamageCommand.Result.NoPermission -> with(kyoriComponentSerializer) {
                commandSender.sendMessage(translation.general.noPermission.let(::toComponent))
            }

            DamageCommand.Result.NoOp -> Unit
            is DamageCommand.Result.Success -> Unit
        }
    }

    private val commandExecutor = CommandExecutor<DamageCommand.Input> {
        with(kyoriComponentSerializer) {
            it.player.sendMessage(translation.custom.damaged(it.damagerName).let(::toComponent))
        }
        it.player.damage(it.damage)
    }

    private fun tabCompleter() = plugin.getCommand(alias)?.setTabCompleter { sender, command, label, args ->
        when (args.size) {
            0 -> listOf("adamage")
            1 -> Bukkit.getOnlinePlayers().map { it.name }
            2 -> listOf(translation.custom.damageHint.raw)
            else -> Bukkit.getOnlinePlayers().map { it.name }
        }
    }

    inner class DamageCommandImpl :
        DamageCommand,
        Command<DamageCommand.Result, DamageCommand.Input> by DefaultCommandFactory.create(
            alias = alias,
            commandParser = commandParser,
            commandExecutor = commandExecutor,
            resultHandler = resultHandler,
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

    override fun create(): DamageCommand {
        tabCompleter()
        return DamageCommandImpl().also {
            it.register(plugin)
        }
    }
}
