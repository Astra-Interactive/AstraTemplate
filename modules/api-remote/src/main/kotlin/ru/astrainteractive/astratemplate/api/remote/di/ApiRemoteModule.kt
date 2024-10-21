package ru.astrainteractive.astratemplate.api.remote.di

import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.api.remote.RickMortyApiImpl
import java.net.http.HttpClient

interface ApiRemoteModule {
    val httpClient: HttpClient
    val rickMortyApi: RickMortyApi

    class Default : ApiRemoteModule {

        override val httpClient: HttpClient by lazy {
            HttpClient.newHttpClient()
        }

        override val rickMortyApi: RickMortyApi by lazy {
            RickMortyApiImpl(httpClient)
        }
    }
}
