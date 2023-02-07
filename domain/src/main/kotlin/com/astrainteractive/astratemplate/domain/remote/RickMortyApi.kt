package com.astrainteractive.astratemplate.domain.remote

import org.jetbrains.kotlin.com.google.gson.Gson
import ru.astrainteractive.astralibs.http.HttpClient
import ru.astrainteractive.astralibs.rest.*
import kotlin.random.Random

interface RickMortyApi {
    suspend fun getRandomCharacter(id: Int): Result<RMResponse>
}