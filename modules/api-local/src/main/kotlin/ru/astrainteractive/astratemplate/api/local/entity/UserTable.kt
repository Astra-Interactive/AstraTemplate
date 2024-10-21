package ru.astrainteractive.astratemplate.api.local.entity

import org.jetbrains.exposed.dao.id.IntIdTable

internal object UserTable : IntIdTable("users") {
    val discordId = text("discord_id")
    val minecraftUuid = text("minecraft_uuid")
}
