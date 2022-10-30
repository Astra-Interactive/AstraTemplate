package com.astrainteractive.astratemplate.domain.local.entities

import ru.astrainteractive.astralibs.database.ColumnInfo
import ru.astrainteractive.astralibs.database.Entity
import ru.astrainteractive.astralibs.database.PrimaryKey
import ru.astrainteractive.astralibs.database_v2.Column
import ru.astrainteractive.astralibs.database_v2.Table

object RatingRelationTable : Table<Int>("rating") {
    override val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val userID = integer("user_id")
    val reason = text("reason")
}
class UserRating : ru.astrainteractive.astralibs.database_v2.Entity<Int>(RatingRelationTable){
    val id by RatingRelationTable.id
    val userID by RatingRelationTable.userID
    val reason by RatingRelationTable.reason
}