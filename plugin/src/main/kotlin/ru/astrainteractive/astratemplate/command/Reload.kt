package ru.astrainteractive.astratemplate.command

import CommandManager
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astratemplate.plugin.Permissions

/**
 * Reload command handler
 */
fun CommandManager.reload() = plugin.registerCommand("atempreload") {
    if (!Permissions.Reload.hasPermission(sender)) {
        sender.sendMessage(translation.general.noPermission)
        return@registerCommand
    }
    sender.sendMessage(translation.general.reload)
    plugin.reloadPlugin()
    sender.sendMessage(translation.general.reloadComplete)
}
