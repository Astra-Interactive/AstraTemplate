package ru.astrainteractive.astratemplate.command.rickmorty

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.command.core.Command
import ru.astrainteractive.astratemplate.command.core.DslCommand
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

class RickMortyCommand(
    private val rickMortyApi: RickMortyApi,
    private val scope: CoroutineScope,
    private val dispatchers: KotlinDispatchers
) : Command by DslCommand(
    alias = "rickmorty",
    block = {
        scope.launch(dispatchers.IO) {
            println("Getting character...")
            println(rickMortyApi.getRandomCharacter(1))
        }
    }
)
