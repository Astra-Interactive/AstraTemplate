package ru.astrainteractive.astratemplate.data.exposed.user.table

import org.jetbrains.exposed.v1.core.dao.id.IntIdTable

internal object UserTable : IntIdTable("users") {
    val discordId = text("discord_id")
    val minecraftUuid = text("minecraft_uuid")
}
