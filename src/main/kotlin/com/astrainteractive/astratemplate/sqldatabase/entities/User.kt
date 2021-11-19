package com.astrainteractive.astratemplate.sqldatabase.entities


import com.astrainteractive.astralibs.catching
import java.sql.ResultSet

data class User(
    val discordId: String,
    val minecraftUuid: String
) {
    companion object {
        fun fromResultSet(rs: ResultSet?) = catching {
            rs?.let {
                return@catching User(
                    discordId = it.getString(discordId.name),
                    minecraftUuid = it.getString(minecraftUuid.name)
                )
            }

        }

        val table: String
            get() = "users"
        val discordId: EntityInfo
            get() = EntityInfo("discord_id", "varchar(16)")
        val minecraftUuid: EntityInfo
            get() = EntityInfo("minecraft_uuid", "varchar(16)")
        val primaryKey: String
            get() = discordId.name
    }
}

data class EntityInfo(val name: String, val type: String)

