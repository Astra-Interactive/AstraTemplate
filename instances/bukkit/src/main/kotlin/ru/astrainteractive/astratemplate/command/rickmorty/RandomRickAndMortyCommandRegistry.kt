package ru.astrainteractive.astratemplate.command.rickmorty

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.error.ErrorHandler
import ru.astrainteractive.astralibs.command.api.util.PluginExt.setCommandExecutor
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import kotlin.random.Random

internal class RandomRickAndMortyCommandRegistry(
    private val plugin: JavaPlugin,
    private val scope: CoroutineScope,
    private val dispatchers: KotlinDispatchers,
    private val rmApi: RickMortyApi,
    private val errorHandler: ErrorHandler<BukkitCommandContext>
) {

    fun register() {
        plugin.setCommandExecutor(
            alias = "rickandmorty",
            commandParser = { commandContext ->
                RandomRickAndMortyCommand.Result(commandContext.sender)
            },
            commandExecutor = { input ->
                scope.launch(dispatchers.IO) {
                    val randomInt = Random.nextInt(0, 100)
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
            errorHandler = errorHandler
        )
    }
}
