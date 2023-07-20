package ru.astrainteractive.astratemplate.api.local

import ru.astrainteractive.astratemplate.api.dto.RatingDTO
import ru.astrainteractive.astratemplate.api.dto.UserDTO
import ru.astrainteractive.astratemplate.api.local.di.LocalApiModule
import ru.astrainteractive.astratemplate.api.local.entities.UserDAO
import ru.astrainteractive.astratemplate.api.local.entities.UserRatingDAO
import ru.astrainteractive.astratemplate.api.local.entities.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entities.UserTable
import java.util.*

internal class LocalApiImpl(
    module: LocalApiModule
) : LocalApi, LocalApiModule by module {

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
