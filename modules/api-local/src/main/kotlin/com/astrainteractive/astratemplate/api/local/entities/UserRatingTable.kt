package com.astrainteractive.astratemplate.api.local.entities

import ru.astrainteractive.astralibs.orm.database.Column
import ru.astrainteractive.astralibs.orm.database.Constructable
import ru.astrainteractive.astralibs.orm.database.Entity
import ru.astrainteractive.astralibs.orm.database.Table

object UserRatingTable : Table<Int>("rating") {
    override val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val userID = integer("user_id")
    val reason = text("reason")
}
class UserRatingDAO : Entity<Int>(UserRatingTable) {
    val id by UserRatingTable.id
    val userID by UserRatingTable.userID
    val reason by UserRatingTable.reason
    companion object : Constructable<UserRatingDAO>(::UserRatingDAO)
}
