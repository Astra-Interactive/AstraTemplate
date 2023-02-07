package com.astrainteractive.astratemplate.domain

import com.astrainteractive.astratemplate.domain.local.dto.UserDTO
import com.astrainteractive.astratemplate.domain.local.dto.mapping.UserMapper
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.User
import com.astrainteractive.astratemplate.domain.local.entities.UserRating
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import com.astrainteractive.astratemplate.domain.remote.RMResponse
import com.astrainteractive.astratemplate.domain.remote.RickMortyApi
import ru.astrainteractive.astralibs.orm.Database
import java.util.*

/**
 * API with all SQL commands
 */
class Repository(
    private val databaseDataSource: Database,
    private val restDataSource: RickMortyApi,
) {
    suspend fun getRandomCharacter(id: Int): Result<RMResponse> {
        return restDataSource.getRandomCharacter(id)
    }

    suspend fun insertUser(user: UserDTO): Int {
        return UserTable.insert(databaseDataSource) {
            this[UserTable.discordId] = user.discordId
            this[UserTable.minecraftUuid] = user.minecraftUUID
        }
    }

    suspend fun insertRating(user: UserDTO): Int {
        return RatingRelationTable.insert(databaseDataSource) {
            this[RatingRelationTable.userID] = user.id
            this[RatingRelationTable.reason] = UUID.randomUUID().toString()
        }
    }

    suspend fun selectRating(user: UserDTO): List<UserRating>? {
        return RatingRelationTable.find(databaseDataSource, UserRating) {
            RatingRelationTable.userID.eq(user.id)
        }
    }

    suspend fun updateUser(user: UserDTO) {
        UserTable.find(databaseDataSource, User) {
            UserTable.id.eq(user.id)
        }.firstOrNull()?.apply {
            this.minecraftUuid = user.minecraftUUID
            this.discordId = user.discordId
            UserTable.update(databaseDataSource, this)
        }
    }

    suspend fun deleteUser(user: UserDTO) {
        UserTable.delete<User>(databaseDataSource) {
            UserTable.id.eq(user.id)
        }
    }

    /**
     * Same as [createUserTable]
     */
    suspend fun getAllUsers(): List<UserDTO>? {
        return UserTable.all(databaseDataSource, User).map(UserMapper::toDTO)
    }
}
