package ru.astrainteractive.astratemplate.api.remote.di

import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.api.remote.RickMortyApiImpl
import java.net.http.HttpClient

class ApiRemoteModule {
    val httpClient: HttpClient by lazy {
        HttpClient.newHttpClient()
    }

    val rickMortyApi: RickMortyApi by lazy {
        RickMortyApiImpl(httpClient)
    }
}
