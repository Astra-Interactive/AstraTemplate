package ru.astrainteractive.astratemplate.commands

import CommandManager
import ru.astrainteractive.astratemplate.modules.RepositoryModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.AstraLibs
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.utils.registerCommand

fun CommandManager.RandomRickAndMortyCharacter() = AstraLibs.registerCommand("rickandmorty") { sender, args ->
    sender.sendMessage("Working on that...")
    val repository = RepositoryModule.value
    PluginScope.launch(Dispatchers.IO) {
        repository.getRandomCharacter()?.let {
            sender.sendMessage("Got response: ${it}")
        }
    }

}
