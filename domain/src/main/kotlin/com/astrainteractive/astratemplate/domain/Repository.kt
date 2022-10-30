package com.astrainteractive.astratemplate.domain

import com.astrainteractive.astratemplate.domain.local.SQLDatabase
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelation
import com.astrainteractive.astratemplate.domain.local.entities.User
import com.astrainteractive.astratemplate.domain.remote.RestApi
import java.util.*
import kotlin.random.Random



/**
 * API with all SQL commands
 */
class Repository(
    private val databaseDataSource: SQLDatabase,
    private val restDataSource: RestApi,
) {
    suspend fun getRandomCharacter(id: Int = Random.nextInt(1, 30)) = restDataSource.getRandomCharacter(id)?.await?.invoke()
    suspend fun insertUser(user: User): Long? {
        return databaseDataSource.insert(user)
    }


    suspend fun insertRating(user: User): Long? {
        return databaseDataSource.insert(RatingRelation(-1, user.id, UUID.randomUUID().toString()))
    }

    suspend fun selectRating(user: User): List<RatingRelation>? {
        return databaseDataSource.select("WHERE user_id=${user.id}")
    }

    suspend fun updateUser(user: User) =
        databaseDataSource.update(user.copy(discordId = "UUU_" + UUID.randomUUID().toString()))

    suspend fun deleteUser(user: User) = databaseDataSource.delete(user)

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<User>? = databaseDataSource.select()
}
