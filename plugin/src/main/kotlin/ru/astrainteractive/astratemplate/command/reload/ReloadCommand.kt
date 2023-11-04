package ru.astrainteractive.astratemplate.command.reload

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.CommandParser
import ru.astrainteractive.astralibs.permission.PermissionManager
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.shared.core.Permissions
import ru.astrainteractive.astratemplate.shared.core.Translation

class ReloadCommand(
    private val permissionManager: PermissionManager,
    private val translation: Translation,
    private val translationContext: BukkitTranslationContext
) : Command, BukkitTranslationContext by translationContext {
    sealed interface Result {
        data object NoPermission : Result
        class Success(val sender: CommandSender) : Result
    }

    class Input(val sender: CommandSender)

    private val commandParser = CommandParser.Default("atempreload") { args, sender ->
        (sender as? Player)?.let {
            val hasPermission = permissionManager.hasPermission(it.uniqueId, Permissions.Damage)
            if (!hasPermission) return@Default Result.NoPermission
        }
        Result.Success(sender)
    }

    override fun register(plugin: JavaPlugin) {
        Command.registerDefault(
            plugin = plugin,
            resultHandler = { commandSender, result ->
                commandSender.sendMessage(translation.general.noPermission)
            },
            commandParser = commandParser,
            commandExecutor = {
                it.sender.sendMessage(translation.general.reload)
                (plugin as AstraTemplate).reloadPlugin()
                it.sender.sendMessage(translation.general.reloadComplete)
            },
            transform = {
                (it as? Result.Success)?.sender?.let(ReloadCommand::Input)
            }
        )
    }
}
