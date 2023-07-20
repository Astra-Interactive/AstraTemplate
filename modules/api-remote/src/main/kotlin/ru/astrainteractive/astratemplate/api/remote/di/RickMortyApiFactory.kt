package ru.astrainteractive.astratemplate.api.remote.di

import ru.astrainteractive.astralibs.http.HttpClient
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.api.remote.RickMortyApiImpl
import ru.astrainteractive.klibs.kdi.Factory

/**
 * Factory method to return [RickMortyApi]
 */
class RickMortyApiFactory(
    private val client: HttpClient
) : Factory<RickMortyApi> {
    override fun create(): RickMortyApi {
        return RickMortyApiImpl(client)
    }
}
