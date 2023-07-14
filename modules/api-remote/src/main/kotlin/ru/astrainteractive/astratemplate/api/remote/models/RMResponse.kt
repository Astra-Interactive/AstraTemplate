package ru.astrainteractive.astratemplate.api.remote.models

import kotlinx.serialization.Serializable

/**
 * Sample response for RickAndMortyAPI
 */
@Serializable
data class RMResponse(
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
)
