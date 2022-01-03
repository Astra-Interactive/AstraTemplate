package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerCommand
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.utils.EmpirePermissions
import com.astrainteractive.astratemplate.utils.Translation

/**
 * Reload command handler
 */
class Reload {

    /**
     * This function called only when atempreload being called
     *
     * Here you should also check for permission
     */
    private val onCommand = AstraLibs.registerCommand("atempreload") { sender, args ->
        if (!sender.hasPermission(EmpirePermissions.reload)) {
            sender.sendMessage(Translation.instance.noPermission)
            return@registerCommand
        }
        sender.sendMessage(AstraTemplate.translations.reload)
        AstraTemplate.instance.reloadPlugin()
        sender.sendMessage(AstraTemplate.translations.reloadComplete)
    }

}