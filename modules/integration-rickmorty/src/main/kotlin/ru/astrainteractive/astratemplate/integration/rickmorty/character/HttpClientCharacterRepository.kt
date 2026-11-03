package ru.astrainteractive.astratemplate.integration.rickmorty.character

import kotlinx.serialization.json.Json
import ru.astrainteractive.astratemplate.integration.rickmorty.character.api.CharacterRepository
import ru.astrainteractive.astratemplate.integration.rickmorty.character.model.RMResponse
import ru.astrainteractive.klibs.mikro.core.coroutines.propagateCancellationException
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers

internal class HttpClientCharacterRepository(
    private val httpClient: HttpClient,
    private val baseUri: URI
) : CharacterRepository {
    private val jsonConfiguration = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun fetchRandomCharacter(id: Int): Result<RMResponse> {
        return runCatching {
            val request: HttpRequest = HttpRequest.newBuilder()
                .uri(baseUri.resolve("character/$id"))
                .GET()
                .build()
            val response = httpClient.send(request, BodyHandlers.ofString())
            jsonConfiguration.decodeFromString<RMResponse>(response.body())
        }.propagateCancellationException()
    }
}
