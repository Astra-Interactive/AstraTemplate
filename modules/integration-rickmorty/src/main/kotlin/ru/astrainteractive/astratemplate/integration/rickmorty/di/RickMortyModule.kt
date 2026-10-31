package ru.astrainteractive.astratemplate.integration.rickmorty.di

import ru.astrainteractive.astratemplate.integration.rickmorty.character.HttpClientCharacterRepository
import ru.astrainteractive.astratemplate.integration.rickmorty.character.api.CharacterRepository
import java.net.URI
import java.net.http.HttpClient

class RickMortyModule {
    val httpClient: HttpClient by lazy {
        HttpClient.newHttpClient()
    }

    val characterRepository: CharacterRepository by lazy {
        HttpClientCharacterRepository(
            httpClient = httpClient,
            baseUri = URI("https://rickandmortyapi.com/api/")
        )
    }
}
