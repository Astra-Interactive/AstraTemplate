package ru.astrainteractive.astratemplate.command.rickmorty

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.command.api.Command
import ru.astrainteractive.astralibs.command.api.CommandParser
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue

class RandomRickAndMortyCommand(
    scope: CoroutineScope,
    private val dispatchers: BukkitDispatchers,
    private val rmApi: RickMortyApi,
    private val randomIntProvider: Provider<Int>
) : Command,
    CoroutineScope by scope {
    class Input(val sender: CommandSender)

    override fun register(plugin: JavaPlugin) {
        Command.registerDefault(
            plugin = plugin,
            resultHandler = CommandParser.ResultHandler.NoOp(),
            commandParser = CommandParser.Default("rickandmorty") { args, sender ->
                Input(sender)
            },
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
            transform = { it }
        )
    }
}
