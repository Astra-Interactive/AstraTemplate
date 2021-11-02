package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.catching
import com.astrainteractive.astratemplate.sqldatabase.entities.User

object Querries {
    fun createUserTable() =
        catching {
            Database.connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS ${User.table} " +
                        "(${User.discordId.name} ${User.discordId.type} NOT NULL, " +
                        "${User.minecraftUuid.name} ${User.minecraftUuid.type} NOT NULL, " +
                        "PRIMARY KEY (${User.primaryKey}));"
            ).execute()
        }

    fun insertUser(user: User) =
        catching {
            Database.connection.createStatement().executeUpdate(
                "INSERT INTO ${User.table} " +
                        "VALUES (\'${user.discordId}\', \'${user.minecraftUuid}\');"
            )
        }

    fun getAllUsers() = catching {
        val rs = Database.connection.createStatement().executeQuery("SELECT * FROM ${User.table}")
        val list = mutableListOf<User>()
        while (rs.next()) {
            val user = User.fromResultSet(rs) ?: continue
            list.add(user)
            println("User = ${user}")
        }
        return@catching list
    }
}

