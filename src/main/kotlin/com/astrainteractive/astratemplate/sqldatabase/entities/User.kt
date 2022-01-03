package com.astrainteractive.astratemplate.sqldatabase.entities


import com.astrainteractive.astralibs.catching
import java.sql.ResultSet
import kotlin.math.min

data class User(
    val id: Long,
    val discordId: String,
    val minecraftUuid: String
) {
    /**
     * Constructor, which allows you to pass [id]
     */
    constructor(discordId: String, minecraftUuid: String) : this(-1L, discordId, minecraftUuid)

    companion object {
        /**
         * This function parse values from [ResultSet]
         */
        fun fromResultSet(rs: ResultSet?) = catching {
            rs?.let {
                return@catching User(
                    id = it.getLong(id.name),
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
        val id: EntityInfo
            get() = EntityInfo("id", "INTEGER")
    }
}


