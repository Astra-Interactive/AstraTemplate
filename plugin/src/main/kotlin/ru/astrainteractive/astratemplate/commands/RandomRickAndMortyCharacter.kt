package ru.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astratemplate.api.remote.RickMortyApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.async.PluginScope
import ru.astrainteractive.astralibs.commands.registerCommand
import ru.astrainteractive.astralibs.di.Dependency
import ru.astrainteractive.astralibs.di.getValue
import ru.astrainteractive.astratemplate.AstraTemplate

fun CommandManager.randomRickAndMortyCharacter(
    rmApiModule: Dependency<RickMortyApi>
) = AstraTemplate.instance.registerCommand("rickandmorty") {
    val rmApi by rmApiModule

    sender.sendMessage("Working on that...")
    PluginScope.launch(Dispatchers.IO) {
        rmApi.getRandomCharacter(1).onSuccess {
            sender.sendMessage("Got response: $it")
        }.onFailure {
            it.printStackTrace()
        }
    }
}
