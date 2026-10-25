@file:Suppress("BareIdentifier")

package ru.astrainteractive.astratemplate.data.dao.user.model

data class Rating(
    val id: Int,
    val userID: Int,
    val reason: String
)
