package ru.astrainteractive.astratemplate.data.exposed.user

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.deleteWhere
import org.jetbrains.exposed.v1.jdbc.insertAndGetId
import org.jetbrains.exposed.v1.jdbc.selectAll
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import ru.astrainteractive.astratemplate.data.dao.user.api.UserRepository
import ru.astrainteractive.astratemplate.data.dao.user.model.Rating
import ru.astrainteractive.astratemplate.data.dao.user.model.User
import ru.astrainteractive.astratemplate.data.exposed.user.table.UserRatingTable
import ru.astrainteractive.astratemplate.data.exposed.user.table.UserTable
import java.util.UUID

internal class ExposedUserRepository(
    private val databaseFlow: Flow<Database>,
) : UserRepository {
    private suspend fun requireDatabase() = databaseFlow.first()

    override suspend fun insertUser(user: User): Int {
        return transaction(requireDatabase()) {
            UserTable.insertAndGetId { statement ->
                statement[UserTable.discordId] = user.discordId
                statement[UserTable.minecraftUuid] = user.minecraftUUID
            }.value
        }
    }

    override suspend fun insertRating(user: User): Int {
        return transaction(requireDatabase()) {
            UserRatingTable.insertAndGetId { statement ->
                statement[UserRatingTable.userID] = user.id
                statement[UserRatingTable.reason] = UUID.randomUUID().toString()
            }.value
        }
    }

    override suspend fun selectRating(user: User): List<Rating> {
        return transaction(requireDatabase()) {
            UserRatingTable.selectAll()
                .where { UserRatingTable.userID.eq(user.id) }
                .map { row ->
                    Rating(
                        id = row[UserRatingTable.id].value,
                        userID = row[UserRatingTable.userID],
                        reason = row[UserRatingTable.reason]
                    )
                }
        }
    }

    override suspend fun updateUser(user: User) {
        return transaction(requireDatabase()) {
            UserTable
                .update(
                    where = {
                        UserTable.id.eq(user.id)
                    },
                    body = { statement ->
                        statement[UserTable.minecraftUuid] = user.minecraftUUID
                        statement[UserTable.discordId] = user.discordId
                    }
                )
        }
    }

    override suspend fun deleteUser(user: User) {
        return transaction(requireDatabase()) {
            UserTable.deleteWhere { UserTable.id.eq(user.id) }
        }
    }

    override suspend fun loadUsers(): List<User> {
        return transaction(requireDatabase()) {
            UserTable
                .selectAll()
                .map { row ->
                    User(
                        id = row[UserTable.id].value,
                        discordId = row[UserTable.discordId],
                        minecraftUUID = row[UserTable.minecraftUuid]
                    )
                }
        }
    }
}
