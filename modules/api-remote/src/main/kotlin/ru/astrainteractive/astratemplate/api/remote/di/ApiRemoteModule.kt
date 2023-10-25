package ru.astrainteractive.astratemplate.api.remote.di

import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.api.remote.RickMortyApiImpl
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue
import java.net.http.HttpClient

interface ApiRemoteModule {
    val httpClient: HttpClient
    val rickMortyApi: RickMortyApi

    class Default : ApiRemoteModule {

        override val httpClient: HttpClient by Single {
            HttpClient.newHttpClient()
        }

        override val rickMortyApi: RickMortyApi by Provider {
            RickMortyApiImpl(httpClient)
        }
    }
}
