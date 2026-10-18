package ru.astrainteractive.astratemplate.integration.rickmorty.di

import ru.astrainteractive.astratemplate.integration.rickmorty.character.HttpClientRickMortyApi
import ru.astrainteractive.astratemplate.integration.rickmorty.character.api.RickMortyApi
import java.net.URI
import java.net.http.HttpClient

class RickMortyModule {
    val httpClient: HttpClient by lazy {
        HttpClient.newHttpClient()
    }

    val rickMortyApi: RickMortyApi by lazy {
        HttpClientRickMortyApi(
            httpClient = httpClient,
            baseUri = URI("https://rickandmortyapi.com/api/")
        )
    }
}
