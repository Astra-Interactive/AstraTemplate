package ru.astrainteractive.astratemplate.command.rickmorty

import kotlinx.coroutines.launch
import ru.astrainteractive.astralibs.command.api.command.Command
import ru.astrainteractive.astralibs.command.api.commandfactory.BukkitCommandFactory
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistry
import ru.astrainteractive.astralibs.command.api.registry.BukkitCommandRegistryContext.Companion.toCommandRegistryContext
import ru.astrainteractive.astralibs.command.api.sideeffect.CommandSideEffect
import ru.astrainteractive.astratemplate.command.rickmorty.di.RickMortyCommandDependencies
import ru.astrainteractive.klibs.kdi.getValue

class RandomRickAndMortyCommandRegistry(
    dependencies: RickMortyCommandDependencies
) : RickMortyCommandDependencies by dependencies {

    fun register() {
        val command = BukkitCommandFactory.create(
            alias = "rickandmorty",
            commandParser = { commandContext ->
                RandomRickAndMortyCommand.Input(commandContext.sender)
            },
            commandSideEffect = CommandSideEffect.NoOp(),
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
        BukkitCommandRegistry.register(
            command = command,
            registryContext = plugin.toCommandRegistryContext()
        )
    }
}
