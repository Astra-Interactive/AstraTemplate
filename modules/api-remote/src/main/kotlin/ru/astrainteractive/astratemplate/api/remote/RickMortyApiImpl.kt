package ru.astrainteractive.astratemplate.api.remote

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.astrainteractive.astralibs.http.HttpClient
import ru.astrainteractive.astratemplate.api.remote.models.RMResponse

/**
 * This is a simple implementation of [RickMortyApi] REST GET request
 */
internal class RickMortyApiImpl(
    private val client: HttpClient
) : RickMortyApi {
    override suspend fun getRandomCharacter(id: Int): Result<RMResponse> = kotlin.runCatching {
        val json = client.get("https://rickandmortyapi.com/api/character/$id").getOrThrow()
        Json.decodeFromString<RMResponse>(json)
    }
}
