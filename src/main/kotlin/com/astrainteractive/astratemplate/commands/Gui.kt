package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.menu.AstraPlayerMenuUtility
import com.astrainteractive.astralibs.registerCommand
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.gui.AstraGui
import com.astrainteractive.astratemplate.utils.EmpirePermissions
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Gui {
    val command = AstraLibs.registerCommand("atempgui"){sender, args ->
        println("Gui command")
        if (sender is Player)
            AstraGui(AstraPlayerMenuUtility(sender)).open()
    }
}