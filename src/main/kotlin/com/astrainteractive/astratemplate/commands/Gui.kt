package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.menu.AstraPlayerMenuUtility
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.gui.AstraGui
import com.astrainteractive.astratemplate.utils.EmpirePermissions
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Gui : CommandExecutor {

    /**
     * This function called only when atempgui beign called
     */
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender is Player)
            AstraGui(AstraPlayerMenuUtility(sender)).open()

        return true

    }
}