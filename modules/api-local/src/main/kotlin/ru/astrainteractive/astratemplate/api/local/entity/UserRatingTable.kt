package ru.astrainteractive.astratemplate.api.local.entity

import ru.astrainteractive.astralibs.orm.database.Column
import ru.astrainteractive.astralibs.orm.database.Constructable
import ru.astrainteractive.astralibs.orm.database.Entity
import ru.astrainteractive.astralibs.orm.database.Table

internal object UserRatingTable : Table<Int>("rating") {
    override val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val userID = integer("user_id")
    val reason = text("reason")
}

internal class UserRatingDAO : Entity<Int>(UserRatingTable) {
    val id by UserRatingTable.id
    val userID by UserRatingTable.userID
    val reason by UserRatingTable.reason
    companion object : Constructable<UserRatingDAO>(::UserRatingDAO)
}
