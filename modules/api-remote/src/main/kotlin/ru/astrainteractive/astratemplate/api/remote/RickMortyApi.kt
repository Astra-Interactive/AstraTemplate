package ru.astrainteractive.astratemplate.api.remote

import ru.astrainteractive.astratemplate.api.remote.model.RMResponse

interface RickMortyApi {
    /**
     * Returns random character by its id
     * @param id - id of the character
     */
    suspend fun getRandomCharacter(id: Int): Result<RMResponse>
}
