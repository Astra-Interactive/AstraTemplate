package ru.astrainteractive.astratemplate.di.factory

import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi.Companion.RickMortyApi
import ru.astrainteractive.klibs.kdi.Factory
import java.net.http.HttpClient

/**
 * Factory method to return [RickMortyApi]
 */
class RickMortyApiFactory(
    private val client: HttpClient = HttpClient.newHttpClient()
) : Factory<RickMortyApi> {
    override fun create(): RickMortyApi {
        return RickMortyApi(client)
    }
}
