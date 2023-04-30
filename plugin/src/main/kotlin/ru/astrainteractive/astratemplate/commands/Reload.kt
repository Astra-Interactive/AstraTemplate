package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule
import ru.astrainteractive.astratemplate.plugin.Permissions

/**
 * Reload command handler
 */
fun CommandManager.reload(
    plugin: AstraTemplate,
    module: CommandManagerModule
) = plugin.registerCommand("atempreload") {
    val translation by module.translationModule

    if (!Permissions.Reload.hasPermission(sender)) {
        sender.sendMessage(translation.noPermission)
        return@registerCommand
    }
    sender.sendMessage(translation.reload)
    plugin.reloadPlugin()
    sender.sendMessage(translation.reloadComplete)
}
