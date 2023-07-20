package ru.astrainteractive.astratemplate.command

import CommandManager
import ru.astrainteractive.astralibs.commands.registerCommand

/**
 * It shows that [val XXX by IReloadable] is actually affected by reloading
 */
fun CommandManager.translation() = plugin.registerCommand("translation") {
    sender.sendMessage(translation.general.getByByCheck)
}
