package com.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astralibs.commands.AstraDSLCommand
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.utils.EmpirePermissions
import com.astrainteractive.astratemplate.utils.Translation

/**
 * Reload command handler
 */

/**
 * This function called only when atempreload being called
 *
 * Here you should also check for permission
 */
fun CommandManager.reload() = AstraDSLCommand.command("atempreload") {
    this.noPermission(EmpirePermissions.reload) {
        sender.sendMessage(Translation.noPermission)
    } ?: return@command
    sender.sendMessage(Translation.reload)
    AstraTemplate.instance.reloadPlugin()
    sender.sendMessage(Translation.reloadComplete)
}






