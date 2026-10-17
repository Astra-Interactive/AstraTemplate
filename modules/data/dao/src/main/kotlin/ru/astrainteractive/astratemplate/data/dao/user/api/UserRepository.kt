package ru.astrainteractive.astratemplate.data.dao.user.api

import ru.astrainteractive.astratemplate.data.dao.user.model.Rating
import ru.astrainteractive.astratemplate.data.dao.user.model.User

interface UserRepository {
    suspend fun insertUser(user: User): Int

    suspend fun insertRating(user: User): Int

    suspend fun selectRating(user: User): List<Rating>

    suspend fun updateUser(user: User)

    suspend fun deleteUser(user: User)

    suspend fun getAllUsers(): List<User>
}
