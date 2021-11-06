package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.AstraCommandBuilder
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.utils.EmpirePermissions
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

/**
 * Reload command handler
 */
class Reload : CommandExecutor {

    /**
     * This function called only when etempreload beign called
     *
     * Here you should also check for permission
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {

        AstraCommandBuilder()
            .label("reload", label)
            .argsSize(0, args.size)
            .sender(sender)
            .example("/reload")
            .explain("Plugin reload")
            .permission("perm1", "perm2")
            .playerCommand {

            }
            .build()





        if (!sender.hasPermission(EmpirePermissions.RELOAD))
            return false
        sender.sendMessage(AstraTemplate.translations.RELOAD)
        AstraTemplate.instance.reloadPlugin()
        sender.sendMessage(AstraTemplate.translations.RELOAD_COMPLETE)
        return true

    }
}