package com.astrainteractive.astratemplate.api.remote

import com.astrainteractive.astratemplate.api.remote.models.RMResponse
import org.jetbrains.kotlin.com.google.gson.Gson
import ru.astrainteractive.astralibs.http.HttpClient

internal class RickMortyApiImpl(
    private val client: HttpClient
) : RickMortyApi {
    override suspend fun getRandomCharacter(id: Int): Result<RMResponse> = kotlin.runCatching {
        val json = client.get("https://rickandmortyapi.com/api/character/$id").getOrThrow()
        Gson().fromJson(json, RMResponse::class.java)
    }
}
