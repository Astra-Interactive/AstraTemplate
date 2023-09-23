package ru.astrainteractive.astratemplate.api.local

import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.api.dto.RatingDTO
import ru.astrainteractive.astratemplate.api.dto.UserDTO
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapper

interface LocalApi {

    suspend fun insertUser(user: UserDTO): Int

    suspend fun insertRating(user: UserDTO): Int

    suspend fun selectRating(user: UserDTO): List<RatingDTO>

    suspend fun updateUser(user: UserDTO)

    suspend fun deleteUser(user: UserDTO)

    suspend fun getAllUsers(): List<UserDTO>

    companion object {
        fun LocalApi(
            database: Database,
            ratingMapper: RatingMapper,
            userMapper: UserMapper
        ): LocalApi = LocalApiImpl(
            database = database,
            ratingMapper = ratingMapper,
            userMapper = userMapper
        )
    }
}
