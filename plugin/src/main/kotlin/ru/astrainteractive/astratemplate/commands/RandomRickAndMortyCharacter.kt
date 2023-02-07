package ru.astrainteractive.astratemplate.commands

import CommandManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.modules.RepositoryModule

fun CommandManager.randomRickAndMortyCharacter() = AstraTemplate.instance.registerCommand("rickandmorty") {
    sender.sendMessage("Working on that...")
    val repository by RepositoryModule
    PluginScope.launch(Dispatchers.IO) {
        repository.getRandomCharacter(1).onSuccess {
            sender.sendMessage("Got response: $it")
        }.onFailure {
            it.printStackTrace()
        }
    }
}
