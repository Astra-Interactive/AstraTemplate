package com.astrainteractive.astratemplate.api.local

import com.astrainteractive.astratemplate.api.dto.RatingDTO
import com.astrainteractive.astratemplate.api.dto.UserDTO

interface LocalApi {
    suspend fun insertUser(user: UserDTO): Int
    suspend fun insertRating(user: UserDTO): Int
    suspend fun selectRating(user: UserDTO): List<RatingDTO>
    suspend fun updateUser(user: UserDTO)
    suspend fun deleteUser(user: UserDTO)
    suspend fun getAllUsers(): List<UserDTO>
}
