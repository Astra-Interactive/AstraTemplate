package com.makeevrserg.empiretemplate.database.entities

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

/**
 * Database Table
 */
object Users: IntIdTable(){
    val discordId: Column<String> = varchar("discord_id",50).uniqueIndex()
    val minecraftUuid:Column<String> = varchar("minecraft_uuid",50)
//    override val primaryKey = PrimaryKey(discordId,name="discord_id")
}

class User(id:EntityID<Int>):IntEntity(id){
    companion object:IntEntityClass<User>(Users)
    var discordId by Users.discordId
    var minecraftUuid by Users.minecraftUuid
}