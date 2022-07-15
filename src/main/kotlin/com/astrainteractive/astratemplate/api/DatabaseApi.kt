package com.astrainteractive.astratemplate.api

import com.astrainteractive.astralibs.utils.catching
import com.astrainteractive.astralibs.utils.mapNotNull
import com.astrainteractive.astratemplate.sqldatabase.SQLDatabase
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import java.sql.Connection


/**
 * API with all SQL commands
 */
object DatabaseApi {
    private val connection: Connection?
        get() = SQLDatabase.connection

    /**
     * Return result on success and null on failure
     * @return null or T
     */
    suspend fun createUserTable() = catching {
        SQLDatabase.createTable(User.table, User.entities)
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun insertUser(user: User): Long? {
        return SQLDatabase.insert(
            User.table,
            User.discordId to user.discordId,
            User.minecraftUuid to user.minecraftUuid
        )
    }

    suspend fun deleteUser(user: User) = SQLDatabase.deleteEntryByID(User.table, User.id.name, user.id)

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<User>? = SQLDatabase.select(User.table, User::fromResultSet)
}
