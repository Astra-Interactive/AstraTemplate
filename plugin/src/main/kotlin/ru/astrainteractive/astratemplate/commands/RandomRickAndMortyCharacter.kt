package ru.astrainteractive.astratemplate.commands

import CommandManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule

fun CommandManager.randomRickAndMortyCharacter(
    plugin: JavaPlugin,
    module: CommandManagerModule
) = plugin.registerCommand("rickandmorty") {
    val rmApi by module.rmApiModule
    val pluginScope by module.pluginScope
    val randomIntProvider = module.randomIntProvider

    sender.sendMessage("Working on that...")
    pluginScope.launch(Dispatchers.IO) {
        rmApi.getRandomCharacter(randomIntProvider.provide()).onSuccess {
            sender.sendMessage("Got response: $it")
        }.onFailure {
            it.printStackTrace()
        }
    }
}
