package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.core.Command
import ru.astrainteractive.astratemplate.command.core.DefaultCommandRegistry
import ru.astrainteractive.astratemplate.command.helloworld.HelloWorldCommand
import ru.astrainteractive.astratemplate.command.rickmorty.RickMortyCommand
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.klibs.mikro.core.dispatchers.DefaultKotlinDispatchers

interface CommandModule {
    val lifecycle: Lifecycle

    class Default(
        private val coreModule: CoreModule,
        private val apiRemoteModule: ApiRemoteModule
    ) : CommandModule {
        private val commands: List<Command>
            get() = listOf(
                HelloWorldCommand(),
                RickMortyCommand(
                    rickMortyApi = apiRemoteModule.rickMortyApi,
                    scope = coreModule.pluginScope.value,
                    dispatchers = DefaultKotlinDispatchers
                )
            )
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    commands.forEach(DefaultCommandRegistry::register)
                }
            )
        }
    }
}
