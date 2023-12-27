package ru.astrainteractive.astratemplate.api.local

import ru.astrainteractive.astratemplate.api.local.model.RatingModel
import ru.astrainteractive.astratemplate.api.local.model.UserModel

interface LocalApi {

    suspend fun insertUser(user: UserModel): Int

    suspend fun insertRating(user: UserModel): Int

    suspend fun selectRating(user: UserModel): List<RatingModel>

    suspend fun updateUser(user: UserModel)

    suspend fun deleteUser(user: UserModel)

    suspend fun getAllUsers(): List<UserModel>
}
