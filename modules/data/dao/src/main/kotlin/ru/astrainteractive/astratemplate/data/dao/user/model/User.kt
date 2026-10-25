@file:Suppress("BareIdentifier")

package ru.astrainteractive.astratemplate.data.dao.user.model

data class User(
    val id: Int,
    val discordId: String,
    val minecraftUUID: String
)
