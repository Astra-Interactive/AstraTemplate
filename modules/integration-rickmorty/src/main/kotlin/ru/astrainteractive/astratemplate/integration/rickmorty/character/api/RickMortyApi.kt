package ru.astrainteractive.astratemplate.integration.rickmorty.character.api

import ru.astrainteractive.astratemplate.integration.rickmorty.character.model.RMResponse

interface RickMortyApi {
    /**
     * Returns random character by its id
     * @param id - id of the character
     */
    suspend fun fetchRandomCharacter(id: Int): Result<RMResponse>
}
