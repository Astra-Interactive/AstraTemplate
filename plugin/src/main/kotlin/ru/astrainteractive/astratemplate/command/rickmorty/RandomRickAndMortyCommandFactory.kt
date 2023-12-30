package ru.astrainteractive.astratemplate.command.rickmorty

import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.DefaultCommandFactory
import ru.astrainteractive.astratemplate.command.rickmorty.di.RickMortyCommandDependencies
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.getValue

class RandomRickAndMortyCommandFactory(
    dependencies: RickMortyCommandDependencies
) : Factory<RandomRickAndMortyCommand>,
    RickMortyCommandDependencies by dependencies {

    private inner class RandomRickAndMortyCommandImpl :
        RandomRickAndMortyCommand,
        Command<RandomRickAndMortyCommand.Input, RandomRickAndMortyCommand.Input> by DefaultCommandFactory.create(
            alias = "rickandmorty",
            commandParser = { args, sender ->
                RandomRickAndMortyCommand.Input(sender)
            },
            resultHandler = Command.ResultHandler.NoOp(),
            commandExecutor = { input ->
                scope.launch(dispatchers.IO) {
                    val randomInt by randomIntProvider
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
            mapper = Command.Mapper.NoOp()
        )

    override fun create(): RandomRickAndMortyCommand {
        return RandomRickAndMortyCommandImpl().also {
            it.register(plugin)
        }
    }
}
