package ru.astrainteractive.astratemplate.integration.rickmorty.character.api

import ru.astrainteractive.astratemplate.integration.rickmorty.character.model.RMResponse

interface CharacterRepository {
    suspend fun fetchRandomCharacter(id: Int): Result<RMResponse>
}
