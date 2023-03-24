package com.astrainteractive.astratemplate.api.remote

import com.astrainteractive.astratemplate.api.remote.models.RMResponse

interface RickMortyApi {
    /**
     * Returns random character by its id
     * @param id - id of the character
     */
    suspend fun getRandomCharacter(id: Int): Result<RMResponse>
}
