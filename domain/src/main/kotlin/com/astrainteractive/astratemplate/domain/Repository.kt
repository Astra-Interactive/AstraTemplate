package com.astrainteractive.astratemplate.domain

import com.astrainteractive.astratemplate.domain.local.dto.RatingDTO
import com.astrainteractive.astratemplate.domain.local.dto.UserDTO
import com.astrainteractive.astratemplate.domain.local.dto.mapping.UserMapper
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.User
import com.astrainteractive.astratemplate.domain.local.entities.UserRating
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import com.astrainteractive.astratemplate.domain.remote.RestApi
import ru.astrainteractive.astralibs.database_v2.Database
import java.util.*
import kotlin.random.Random


/**
 * API with all SQL commands
 */
class Repository(
    private val databaseDataSource: Database,
    private val restDataSource: RestApi,
) {
    suspend fun getRandomCharacter(id: Int = Random.nextInt(1, 30)) =
        restDataSource.getRandomCharacter(id)?.await?.invoke()

    suspend fun insertUser(user: UserDTO): Int {
        return UserTable.insert {
            this[UserTable.discordId] = user.discordId
            this[UserTable.minecraftUuid] = user.minecraftUUID
        }
    }


    suspend fun insertRating(user: UserDTO): Int {
        return RatingRelationTable.insert {
            this[RatingRelationTable.userID] = user.id
            this[RatingRelationTable.reason] = UUID.randomUUID().toString()
        }
    }

    suspend fun selectRating(user: UserDTO): List<UserRating>? {
        return RatingRelationTable.find(databaseDataSource, ::UserRating) {
            RatingRelationTable.userID.eq(user.id)
        }
    }

    suspend fun updateUser(user: UserDTO) {
        UserTable.find(constructor = ::User) {
            UserTable.id.eq(user.id)
        }.firstOrNull()?.apply {
            this.minecraftUuid = user.minecraftUUID
            this.discordId = user.discordId
            UserTable.update(databaseDataSource, this)
        }
    }

    suspend fun deleteUser(user: UserDTO) {
        UserTable.delete<User> {
            UserTable.id.eq(user.id)
        }
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<UserDTO>? {
        return UserTable.all(databaseDataSource, ::User).map(UserMapper::toDTO)
    }
}

