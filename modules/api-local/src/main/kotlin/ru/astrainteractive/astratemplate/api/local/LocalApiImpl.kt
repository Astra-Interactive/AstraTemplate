package ru.astrainteractive.astratemplate.api.local

import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.api.dto.RatingDTO
import ru.astrainteractive.astratemplate.api.dto.UserDTO
import ru.astrainteractive.astratemplate.api.local.entity.UserDAO
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingDAO
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entity.UserTable
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapper
import java.util.UUID

internal class LocalApiImpl(
    private val database: Database,
    private val ratingMapper: RatingMapper,
    private val userMapper: UserMapper
) : LocalApi {

    override suspend fun insertUser(user: UserDTO): Int {
        return UserTable.insert(database) {
            this[UserTable.discordId] = user.discordId
            this[UserTable.minecraftUuid] = user.minecraftUUID
        }
    }

    override suspend fun insertRating(user: UserDTO): Int {
        return UserRatingTable.insert(database) {
            this[UserRatingTable.userID] = user.id
            this[UserRatingTable.reason] = UUID.randomUUID().toString()
        }
    }

    override suspend fun selectRating(user: UserDTO): List<RatingDTO> {
        return UserRatingTable.find(database, UserRatingDAO) {
            UserRatingTable.userID.eq(user.id)
        }.map(ratingMapper::toDTO)
    }

    override suspend fun updateUser(user: UserDTO) {
        UserTable.find(database, UserDAO) {
            UserTable.id.eq(user.id)
        }.firstOrNull()?.apply {
            this.minecraftUuid = user.minecraftUUID
            this.discordId = user.discordId
            UserTable.update(database, this)
        }
    }

    override suspend fun deleteUser(user: UserDTO) {
        UserTable.delete<UserDAO>(database) {
            UserTable.id.eq(user.id)
        }
    }

    override suspend fun getAllUsers(): List<UserDTO> {
        return UserTable.all(database, UserDAO).map(userMapper::toDTO)
    }
}
