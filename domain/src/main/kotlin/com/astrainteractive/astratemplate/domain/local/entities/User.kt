package com.astrainteractive.astratemplate.domain.local.entities

import ru.astrainteractive.astralibs.database.ColumnInfo
import ru.astrainteractive.astralibs.database.Entity
import ru.astrainteractive.astralibs.database.PrimaryKey
import ru.astrainteractive.astralibs.database_v2.Column
import ru.astrainteractive.astralibs.database_v2.Table

object UserTable : Table<Int>("users") {
    override val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val discordId = text("discord_id")
    val minecraftUuid = text("minecraft_uuid")
}

class User : ru.astrainteractive.astralibs.database_v2.Entity<Int>(UserTable) {
    val id by UserTable.id
    var discordId by UserTable.discordId
    var minecraftUuid by UserTable.minecraftUuid
}