package ru.astrainteractive.astratemplate.data.dao.user.memory

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.astrainteractive.astratemplate.data.dao.user.api.UserRepository
import ru.astrainteractive.astratemplate.data.dao.user.model.Rating
import ru.astrainteractive.astratemplate.data.dao.user.model.User
import java.util.UUID

@Suppress("ImplementationVisibility")
class InMemoryUserRepository : UserRepository {
    private val mutex = Mutex()
    private val users = mutableListOf<User>()
    private val ratings = mutableListOf<Rating>()
    private var lastUserId = 0
    private var lastRatingId = 0

    override suspend fun insertUser(user: User): Int {
        return mutex.withLock {
            lastUserId += 1
            users += user.copy(id = lastUserId)
            lastUserId
        }
    }

    override suspend fun insertRating(user: User): Int {
        return mutex.withLock {
            lastRatingId += 1
            ratings += Rating(
                id = lastRatingId,
                userID = user.id,
                reason = UUID.randomUUID().toString()
            )
            lastRatingId
        }
    }

    override suspend fun selectRating(user: User): List<Rating> {
        return mutex.withLock {
            ratings.filter { rating -> rating.userID == user.id }
        }
    }

    override suspend fun updateUser(user: User) {
        mutex.withLock {
            users.replaceAll { stored -> if (stored.id == user.id) user else stored }
        }
    }

    override suspend fun deleteUser(user: User) {
        mutex.withLock { users.removeAll { stored -> stored.id == user.id } }
    }

    override suspend fun loadUsers(): List<User> {
        return mutex.withLock { users.toList() }
    }
}
