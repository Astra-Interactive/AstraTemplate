package ru.astrainteractive.astratemplate.command.rickmorty

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.DefaultCommandFactory
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

class RandomRickAndMortyCommandFactory(
    private val plugin: JavaPlugin,
    scope: CoroutineScope,
    private val dispatchers: BukkitDispatchers,
    private val rmApi: RickMortyApi,
    private val randomIntProvider: Provider<Int>
) : Factory<RandomRickAndMortyCommand>,
    CoroutineScope by scope {

    private inner class RandomRickAndMortyCommandImpl :
        RandomRickAndMortyCommand,
        Command<RandomRickAndMortyCommand.Input, RandomRickAndMortyCommand.Input> by DefaultCommandFactory.create(
            alias = "rickandmorty",
            commandParser = { args, sender ->
                RandomRickAndMortyCommand.Input(sender)
            },
            resultHandler = Command.ResultHandler.NoOp(),
            commandExecutor = { input ->
                launch(dispatchers.IO) {
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
