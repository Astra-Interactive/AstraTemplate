package com.astrainteractive.astratemplate.domain.local.entities

import ru.astrainteractive.astralibs.database.ColumnInfo
import ru.astrainteractive.astralibs.database.Entity
import ru.astrainteractive.astralibs.database.PrimaryKey

@Entity(RatingRelation.TABLE)
data class RatingRelation(
    @PrimaryKey(autoIncrement = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "user_id")
    val userID: Long,
    @ColumnInfo(name = "reason")
    val reason: String,
) {
    companion object {
        const val TABLE: String = "rating"
    }
}
