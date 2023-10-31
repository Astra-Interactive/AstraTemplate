package ru.astrainteractive.astratemplate.command

import CommandManager
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.registerCommand
import ru.astrainteractive.astratemplate.shared.core.Permissions
import ru.astrainteractive.klibs.kdi.getValue

/**
 * Reload command handler
 */
fun CommandManager.reload() = plugin.registerCommand("atempreload") {
    (sender as? Player)?.let {
        if (permissionManager.hasPermission(it.uniqueId, Permissions.Damage)) return@let
        sender.sendMessage(translation.general.noPermission)
        return@registerCommand
    }
    sender.sendMessage(translation.general.reload)
    plugin.reloadPlugin()
    sender.sendMessage(translation.general.reloadComplete)
}
