package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.catching
import com.astrainteractive.astralibs.mapNotNull
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import java.sql.Connection


/**
 * API with all SQL commands
 */
object DatabaseApi {
    private val connection: Connection
        get() = Database.connection

    /**
     * Return result on success and null on failure
     * @return null or T
     */
    suspend fun createUserTable() = catching {
        connection.prepareStatement(
            "CREATE TABLE IF NOT EXISTS ${User.table} " +
                    "(" +
                    "${User.id.name} ${User.id.type} PRIMARY KEY AUTOINCREMENT," +
                    "${User.discordId.name} ${User.discordId.type} NOT NULL, " +
                    "${User.minecraftUuid.name} ${User.minecraftUuid.type} NOT NULL" +
                    ");"
        ).execute()
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun insertUser(user: User): Int? = catching {
        val query = "INSERT INTO ${User.table} " +
                "(${User.discordId.name}, ${User.minecraftUuid.name}) " +
                "VALUES(\'${user.discordId}\', \'${user.minecraftUuid}\');"
        connection.prepareStatement(query).executeUpdate()
    }

    suspend fun deleteUser(user: User) = catching {
        val query = "DELETE FROM ${User.table} WHERE ${User.id.name}=${user.id}"
        return@catching Database.connection.prepareStatement(query).execute()
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<User>? = catching {
        val rs = connection.createStatement().executeQuery("SELECT * FROM ${User.table}")
        rs.mapNotNull { User.fromResultSet(it) }
    }
}
