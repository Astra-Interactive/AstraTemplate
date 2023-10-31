package ru.astrainteractive.astratemplate.api.local.entity

import ru.astrainteractive.astralibs.orm.database.Column
import ru.astrainteractive.astralibs.orm.database.Constructable
import ru.astrainteractive.astralibs.orm.database.Entity
import ru.astrainteractive.astralibs.orm.database.Table

internal object UserTable : Table<Int>("users") {
    override val id: Column<Int> = integer("id").primaryKey().autoIncrement()
    val discordId = text("discord_id")
    val minecraftUuid = text("minecraft_uuid")
}

internal class UserDAO : Entity<Int>(UserTable) {
    val id by UserTable.id
    var discordId by UserTable.discordId
    var minecraftUuid by UserTable.minecraftUuid
    companion object : Constructable<UserDAO>(::UserDAO)
}
