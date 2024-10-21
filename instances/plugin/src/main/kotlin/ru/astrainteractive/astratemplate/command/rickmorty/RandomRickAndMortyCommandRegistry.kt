package ru.astrainteractive.astratemplate.command.rickmorty

import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.command.api.util.PluginExt.registerCommand
import ru.astrainteractive.astratemplate.command.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.rickmorty.di.RickMortyCommandDependencies

internal class RandomRickAndMortyCommandRegistry(
    dependencies: RickMortyCommandDependencies
) : RickMortyCommandDependencies by dependencies {

    fun register() {
        plugin.registerCommand(
            alias = "rickandmorty",
            commandParser = { commandContext ->
                RandomRickAndMortyCommand.Result(commandContext.sender)
            },
            commandExecutor = { input ->
                scope.launch(dispatchers.IO) {
                    val randomInt = getRabdomInt()
                    val result = rmApi.getRandomCharacter(randomInt)
                    result.onSuccess {
                        input.sender.sendMessage("Got response: $it")
                    }
                    result.onFailure {
                        it.printStackTrace()
                        input.sender.sendMessage("Fail: ${it.message}")
                    }
                }
            },
            errorHandler = DefaultErrorHandler(
                translationKrate = translationKrate,
                kyoriKrate = kyoriKrate
            )
        )
    }
}
