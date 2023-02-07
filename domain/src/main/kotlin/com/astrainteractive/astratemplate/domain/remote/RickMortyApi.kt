package com.astrainteractive.astratemplate.domain.remote
interface RickMortyApi {
    suspend fun getRandomCharacter(id: Int): Result<RMResponse>
}
