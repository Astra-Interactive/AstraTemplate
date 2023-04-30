package ru.astrainteractive.astratemplate.commands

import CommandManager
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule

fun CommandManager.tempGUI(
    plugin: JavaPlugin,
    module: CommandManagerModule
) = plugin.registerCommand("atempgui") {
    val pluginScope by module.pluginScope
    val dispatchers by module.dispatchers
    val player = sender as? Player ?: return@registerCommand
    pluginScope.launch(dispatchers.BukkitAsync) {
        val sampleGUI = module.sampleGuiFactory(player = player).build()
        withContext(dispatchers.BukkitMain) { sampleGUI.open() }
    }
}
