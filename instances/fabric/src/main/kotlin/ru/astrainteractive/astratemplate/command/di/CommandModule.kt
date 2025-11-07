package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.core.Command
import ru.astrainteractive.astratemplate.command.core.DefaultCommandRegistry
import ru.astrainteractive.astratemplate.command.helloworld.HelloWorldCommand
import ru.astrainteractive.astratemplate.command.rickmorty.RickMortyCommand
import ru.astrainteractive.astratemplate.core.di.CoreModule

class CommandModule(
    private val coreModule: CoreModule,
    private val apiRemoteModule: ApiRemoteModule
) {
    private val commands: List<Command> = listOf(
        HelloWorldCommand(),
        RickMortyCommand(
            rickMortyApi = apiRemoteModule.rickMortyApi,
            ioScope = coreModule.ioScope,
            dispatchers = coreModule.dispatchers
        )
    )
    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda(
            onEnable = {
                commands.forEach(DefaultCommandRegistry::register)
            }
        )
    }
}
