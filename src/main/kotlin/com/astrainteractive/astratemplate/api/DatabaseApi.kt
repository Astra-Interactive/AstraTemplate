package com.astrainteractive.astratemplate.api

import com.astrainteractive.astralibs.utils.catching
import com.astrainteractive.astratemplate.sqldatabase.SQLDatabase
import com.astrainteractive.astratemplate.sqldatabase.entities.RatingRelation
import com.astrainteractive.astratemplate.sqldatabase.entities.User
import java.sql.Connection
import java.util.*


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
        SQLDatabase.createTable<User>()
    }

    suspend fun createRatingTable() = catching {
        SQLDatabase.createTable<RatingRelation>()
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun insertUser(user: User): Long? {
        return SQLDatabase.insert(user)
    }


    suspend fun insertRating(user: User): Long? {
        return SQLDatabase.insert(RatingRelation(-1, user.id, UUID.randomUUID().toString()))
    }

    suspend fun selectRating(user: User): List<RatingRelation>? {
        return SQLDatabase.select("WHERE user_id=${user.id}")
    }

    suspend fun updateUser(user: User) =
        SQLDatabase.update(user.copy(discordId = "UUU_" + UUID.randomUUID().toString()))

    suspend fun deleteUser(user: User) = SQLDatabase.delete(user)

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<User>? = SQLDatabase.select()
}
