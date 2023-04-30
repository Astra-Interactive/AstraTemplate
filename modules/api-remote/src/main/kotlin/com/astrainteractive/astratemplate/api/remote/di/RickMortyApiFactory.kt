package com.astrainteractive.astratemplate.api.remote.di

import com.astrainteractive.astratemplate.api.remote.RickMortyApi
import com.astrainteractive.astratemplate.api.remote.RickMortyApiImpl
import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.http.HttpClient

/**
 * Factory method to return [RickMortyApi]
 */
class RickMortyApiFactory(
    private val client: HttpClient
) : Factory<RickMortyApi> {
    override fun build(): RickMortyApi {
        return RickMortyApiImpl(client)
    }
}
