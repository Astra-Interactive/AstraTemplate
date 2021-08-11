package com.makeevrserg.empiretemplate.commands

import com.makeevrserg.empiretemplate.EmpireTemplate
import com.makeevrserg.empiretemplate.utils.EmpirePermissions
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

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
        if (!sender.hasPermission(EmpirePermissions.RELOAD))
            return false
        sender.sendMessage(EmpireTemplate.translation.RELOAD)
        EmpireTemplate.instance.reloadPlugin()
        sender.sendMessage(EmpireTemplate.translation.RELOAD_COMPLETE)
        return true

    }
}