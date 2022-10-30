package com.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.modules.TranslationProvider
import com.astrainteractive.astratemplate.utils.AstraPermission
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.utils.registerCommand

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






