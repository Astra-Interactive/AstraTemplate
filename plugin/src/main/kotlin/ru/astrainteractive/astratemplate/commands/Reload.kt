package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.plugin.Permissions
import ru.astrainteractive.astratemplate.plugin.Translation

/**
 * Reload command handler
 */

/**
 * This function called only when atempreload being called
 *
 * Here you should also check for permission
 */
fun CommandManager.reload(
    translationModule: Dependency<Translation>
) = AstraTemplate.instance.registerCommand("atempreload") {
    val translation by translationModule
    if (!Permissions.Reload.hasPermission(sender)) {
        sender.sendMessage(translation.noPermission)
        return@registerCommand
    }
    sender.sendMessage(translation.reload)
    AstraTemplate.instance.reloadPlugin()
    sender.sendMessage(translation.reloadComplete)
}
