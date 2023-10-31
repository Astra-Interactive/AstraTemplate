package ru.astrainteractive.astratemplate.api.remote

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.astrainteractive.astratemplate.api.remote.model.RMResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

/**
 * This is a simple implementation of [RickMortyApi] REST GET request
 */
internal class RickMortyApiImpl(
    private val httpClient: HttpClient
) : RickMortyApi {
    private val jsonConfiguration = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun getRandomCharacter(id: Int): Result<RMResponse> = kotlin.runCatching {
        val request: HttpRequest = HttpRequest.newBuilder()
            .uri(URI("https://rickandmortyapi.com/api/character/$id"))
            .GET()
            .build()
        val response = httpClient.send(request, BodyHandlers.ofString())
        val json = response.body()
        jsonConfiguration.decodeFromString<RMResponse>(json)
    }
}
