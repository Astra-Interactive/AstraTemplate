package ru.astrainteractive.astratemplate.integration.rickmorty.character.httpclient

import kotlinx.serialization.json.Json
import ru.astrainteractive.astratemplate.integration.rickmorty.character.api.RickMortyApi
import ru.astrainteractive.astratemplate.integration.rickmorty.character.model.RMResponse
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse.BodyHandlers
import kotlin.coroutines.cancellation.CancellationException

internal class HttpClientRickMortyApi(
    private val httpClient: HttpClient,
    private val baseUri: URI
) : RickMortyApi {
    private val jsonConfiguration = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override suspend fun getRandomCharacter(id: Int): Result<RMResponse> {
        return runCatching {
            val request: HttpRequest = HttpRequest.newBuilder()
                .uri(baseUri.resolve("character/$id"))
                .GET()
                .build()
            val response = httpClient.send(request, BodyHandlers.ofString())
            jsonConfiguration.decodeFromString<RMResponse>(response.body())
        }.onFailure { throwable -> if (throwable is CancellationException) throw throwable }
    }
}
