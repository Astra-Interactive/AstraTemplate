package ru.astrainteractive.astratemplate.command

import CommandManager
import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.klibs.kdi.getValue

fun CommandManager.randomRickAndMortyCharacter() = plugin.registerCommand("rickandmorty") {
    sender.sendMessage("Working on that...")
    pluginScope.launch(dispatchers.IO) {
        val randomInt by randomIntProvider
        rmApi.getRandomCharacter(randomInt).onSuccess {
            sender.sendMessage("Got response: $it")
        }.onFailure {
            it.printStackTrace()
            sender.sendMessage("Fail: ${it.message}")
        }
    }
}
