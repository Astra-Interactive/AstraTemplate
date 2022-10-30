package com.astrainteractive.astratemplate.domain.local.entities

import ru.astrainteractive.astralibs.database.ColumnInfo
import ru.astrainteractive.astralibs.database.Entity
import ru.astrainteractive.astralibs.database.PrimaryKey

@Entity(User.TABLE)
data class User(
    @PrimaryKey(autoIncrement = true)
    @ColumnInfo(name = "id")
    val id: Long,
    @ColumnInfo(name = "discord_id")
    val discordId: String,
    @ColumnInfo(name = "minecraft_uuid")
    val minecraftUuid: String,
) {
    companion object {
        const val TABLE: String = "users"
    }
}
