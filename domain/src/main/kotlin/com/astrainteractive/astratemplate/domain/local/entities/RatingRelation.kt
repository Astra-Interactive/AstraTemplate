package com.astrainteractive.astratemplate.domain.local.entities

import ru.astrainteractive.astralibs.orm.database.Column
import ru.astrainteractive.astralibs.orm.database.Constructable
import ru.astrainteractive.astralibs.orm.database.Entity
import ru.astrainteractive.astralibs.orm.database.Table

object RatingRelationTable : Table<Int>("rating") {
    override val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val userID = integer("user_id")
    val reason = text("reason")
}
class UserRating : Entity<Int>(RatingRelationTable) {
    val id by RatingRelationTable.id
    val userID by RatingRelationTable.userID
    val reason by RatingRelationTable.reason
    companion object : Constructable<UserRating>(::UserRating)
}
