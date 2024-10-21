package ru.astrainteractive.astratemplate.api.local.entity

import org.jetbrains.exposed.dao.id.IntIdTable

internal object UserRatingTable : IntIdTable("rating") {
    val userID = integer("user_id")
    val reason = text("reason")
}
