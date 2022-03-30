package com.astrainteractive.astratemplate.sqldatabase

import com.astrainteractive.astralibs.catching
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import com.astrainteractive.astratemplate.utils.callbackCatching
import com.astrainteractive.astratemplate.utils.forEach
import com.astrainteractive.astratemplate.utils.mapNotNull
import java.sql.Connection
import java.sql.ResultSet

/**
 * Repository with all SQL commands
 */
object Repository {
    private val connection: Connection
        get() = Database.connection

    /**
     * Return result on success and null on failure
     *
     * If [callback] initialized - also returns result in [Callback.onSuccess] as returns exception in [Callback.onFailure]
     * @param callback Callback function
     * @return null or T
     */
    suspend fun createUserTable() = connection.prepareStatement(
        "CREATE TABLE IF NOT EXISTS ${User.table} " +
                "(" +
                "${User.id.name} ${User.id.type} PRIMARY KEY AUTOINCREMENT," +
                "${User.discordId.name} ${User.discordId.type} NOT NULL, " +
                "${User.minecraftUuid.name} ${User.minecraftUuid.type} NOT NULL" +
                ");"
    ).execute()

    enum class SqlDataType(val size: Int? = null) {
        BIT(64), TINYINT(255), BOOL, BOOLEAN, SMALLINT(255);

        override fun toString(): String {
            when (this) {
                BIT -> "${this.name}(${this.size})"
                else -> this.name
            }
            return super.toString()
        }
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun insertUser(user: User): Int {
        val query = "INSERT INTO ${User.table} " +
                "(${User.discordId.name}, ${User.minecraftUuid.name}) " +
                "VALUES(\'${user.discordId}\', \'${user.minecraftUuid}\');"
        return connection.prepareStatement(query).executeUpdate()
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<User> {
            val rs = connection.createStatement().executeQuery("SELECT * FROM ${User.table}")
            return rs.mapNotNull { User.fromResultSet(it) }
        }
}
