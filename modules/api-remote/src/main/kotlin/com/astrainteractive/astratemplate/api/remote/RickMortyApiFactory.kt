package com.astrainteractive.astratemplate.api.remote

import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.astralibs.http.HttpClient

/**
 * Factory method to return [RickMortyApi]
 */
class RickMortyApiFactory(
    private val client: HttpClient = HttpClient
) : Factory<RickMortyApi>() {
    override fun initializer(): RickMortyApi {
        return RickMortyApiImpl(client)
    }
}
