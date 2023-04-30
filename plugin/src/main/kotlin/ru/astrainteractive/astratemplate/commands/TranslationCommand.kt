package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule

/**
 * It shows that [val XXX by IReloadable] is actually affected by reloading
 */
fun CommandManager.translation(
    plugin: AstraTemplate,
    module: CommandManagerModule
) = plugin.registerCommand("translation") {
    val translation by module.translationModule

    sender.sendMessage(translation.getByByCheck)
}
