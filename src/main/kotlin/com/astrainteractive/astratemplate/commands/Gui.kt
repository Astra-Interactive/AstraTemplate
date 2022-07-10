package com.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerCommand
import com.astrainteractive.astratemplate.gui.SampleGUI
import org.bukkit.entity.Player

fun CommandManager.tempGUI() = AstraLibs.registerCommand("atempgui") { sender, args ->
    if (sender is Player)
        SampleGUI(sender).open()
}
