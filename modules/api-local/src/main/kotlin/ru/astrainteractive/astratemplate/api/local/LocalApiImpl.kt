package ru.astrainteractive.astratemplate.api.local

import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.api.local.entity.UserDAO
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingDAO
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entity.UserTable
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapper
import ru.astrainteractive.astratemplate.api.local.model.RatingModel
import ru.astrainteractive.astratemplate.api.local.model.UserModel
import java.util.UUID

internal class LocalApiImpl(
    private val database: Database,
    private val ratingMapper: RatingMapper,
    private val userMapper: UserMapper
) : LocalApi {

    override suspend fun insertUser(user: UserModel): Int {
        return UserTable.insert(database) {
            this[UserTable.discordId] = user.discordId
            this[UserTable.minecraftUuid] = user.minecraftUUID
        }
    }

    override suspend fun insertRating(user: UserModel): Int {
        return UserRatingTable.insert(database) {
            this[UserRatingTable.userID] = user.id
            this[UserRatingTable.reason] = UUID.randomUUID().toString()
        }
    }

    override suspend fun selectRating(user: UserModel): List<RatingModel> {
        return UserRatingTable.find(database, UserRatingDAO) {
            UserRatingTable.userID.eq(user.id)
        }.map(ratingMapper::toDTO)
    }

    override suspend fun updateUser(user: UserModel) {
        UserTable.find(database, UserDAO) {
            UserTable.id.eq(user.id)
        }.firstOrNull()?.apply {
            this.minecraftUuid = user.minecraftUUID
            this.discordId = user.discordId
            UserTable.update(database, this)
        }
    }

    override suspend fun deleteUser(user: UserModel) {
        UserTable.delete<UserDAO>(database) {
            UserTable.id.eq(user.id)
        }
    }

    override suspend fun getAllUsers(): List<UserModel> {
        return UserTable.all(database, UserDAO).map(userMapper::toDTO)
    }
}
