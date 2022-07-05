package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerCommand
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.commands.AstraDSLCommand.dslCommand
import com.astrainteractive.astratemplate.utils.EmpirePermissions
import com.astrainteractive.astratemplate.utils.Translation
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

/**
 * Reload command handler
 */
class Reload {

    /**
     * This function called only when atempreload being called
     *
     * Here you should also check for permission
     */
    private val reload = AstraDSLCommand.command("atempreload") {
        this.noPermission(EmpirePermissions.reload) {
            sender.sendMessage(Translation.noPermission)
        } ?: return@command
        sender.sendMessage(AstraTemplate.translations.reload)
        AstraTemplate.instance.reloadPlugin()
        sender.sendMessage(AstraTemplate.translations.reloadComplete)
    }


}




