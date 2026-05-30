package ru.astrainteractive.astratemplate.api.local.dao

import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import ru.astrainteractive.astratemplate.api.local.entity.UserRatingTable
import ru.astrainteractive.astratemplate.api.local.entity.UserTable
import ru.astrainteractive.astratemplate.api.local.model.RatingModel
import ru.astrainteractive.astratemplate.api.local.model.UserModel

internal class LocalDaoImpl(
    private val databaseFlow: Flow<Database>,
) : LocalDao {
    private suspend fun requireDatabase() = databaseFlow.first()

    override suspend fun insertUser(
        user: UserModel
    ): Int = transaction(requireDatabase()) {
        UserTable.insertAndGetId {
            it[UserTable.discordId] = user.discordId
            it[UserTable.minecraftUuid] = user.minecraftUUID
        }.value
    }

    override suspend fun insertRating(
        user: UserModel
    ): Int = transaction(requireDatabase()) {
        UserRatingTable.insertAndGetId {
            it[UserRatingTable.userID] = user.id
            it[UserRatingTable.reason] = UUID.randomUUID().toString()
        }.value
    }

    override suspend fun selectRating(
        user: UserModel
    ): List<RatingModel> = transaction(requireDatabase()) {
        UserRatingTable.selectAll()
            .where { UserRatingTable.userID.eq(user.id) }
            .map {
                RatingModel(
                    id = it[UserRatingTable.id].value,
                    userID = it[UserRatingTable.userID],
                    reason = it[UserRatingTable.reason]
                )
            }
    }

    override suspend fun updateUser(
        user: UserModel
    ): Unit = transaction(requireDatabase()) {
        UserTable
            .update(
                where = {
                    UserTable.id.eq(user.id)
                },
                body = {
                    it[UserTable.minecraftUuid] = user.minecraftUUID
                    it[UserTable.discordId] = user.discordId
                }
            )
    }

    override suspend fun deleteUser(
        user: UserModel
    ): Unit = transaction(requireDatabase()) {
        UserTable.deleteWhere { UserTable.id.eq(user.id) }
    }

    override suspend fun getAllUsers(): List<UserModel> = transaction(requireDatabase()) {
        UserTable
            .selectAll()
            .map {
                UserModel(
                    id = it[UserTable.id].value,
                    discordId = it[UserTable.discordId],
                    minecraftUUID = it[UserTable.minecraftUuid]
                )
            }
    }
}
