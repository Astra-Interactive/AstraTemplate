package com.astrainteractive.astratemplate.api

import com.astrainteractive.astralibs.utils.Injector.inject
import com.astrainteractive.astratemplate.api.remote.RestApi
import com.astrainteractive.astratemplate.api.local.SQLDatabase
import com.astrainteractive.astratemplate.api.local.RatingRelation
import com.astrainteractive.astratemplate.api.local.User
import java.util.*
import kotlin.random.Random



/**
 * API with all SQL commands
 */
class Repository(
    private val databaseDataSource: SQLDatabase? = inject(),
    private val restDataSource: RestApi? = inject(),
) {
    suspend fun getRandomCharacter(id: Int = Random.nextInt(1, 30)) = restDataSource?.getRandomCharacter(id)?.await?.invoke()
    suspend fun insertUser(user: User): Long? {
        return databaseDataSource?.insert(user)
    }


    suspend fun insertRating(user: User): Long? {
        return databaseDataSource?.insert(RatingRelation(-1, user.id, UUID.randomUUID().toString()))
    }

    suspend fun selectRating(user: User): List<RatingRelation>? {
        return databaseDataSource?.select("WHERE user_id=${user.id}")
    }

    suspend fun updateUser(user: User) =
        databaseDataSource?.update(user.copy(discordId = "UUU_" + UUID.randomUUID().toString()))

    suspend fun deleteUser(user: User) = databaseDataSource?.delete(user)

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<User>? = databaseDataSource?.select()
}
