package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.modules.TranslationProvider
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.utils.registerCommand
import ru.astrainteractive.astratemplate.utils.AstraPermission

/**
 * Reload command handler
 */

/**
 * This function called only when atempreload being called
 *
 * Here you should also check for permission
 */
fun CommandManager.reload() = AstraLibs.registerCommand("atempreload") { sender, args ->
    val translation = TranslationProvider.value
    if (!AstraPermission.Reload.hasPermission(sender)) {
        sender.sendMessage(translation.noPermission)
        return@registerCommand
    }
    sender.sendMessage(translation.reload)
    AstraTemplate.instance.reloadPlugin()
    sender.sendMessage(translation.reloadComplete)
}






