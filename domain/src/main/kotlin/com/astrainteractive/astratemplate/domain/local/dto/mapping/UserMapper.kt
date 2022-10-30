package com.astrainteractive.astratemplate.domain.local.dto.mapping

import com.astrainteractive.astratemplate.domain.local.dto.RatingDTO
import com.astrainteractive.astratemplate.domain.local.dto.UserDTO
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.User
import com.astrainteractive.astratemplate.domain.local.entities.UserRating
import com.astrainteractive.astratemplate.domain.local.entities.UserTable
import ru.astrainteractive.astralibs.domain.mapping.IMapper

interface IUserMapper : IMapper<User, UserDTO>

object UserMapper : IUserMapper {
    override fun fromDTO(it: UserDTO): User {
        return UserTable.find(constructor = ::User) {
            UserTable.id.eq(it.id)
        }.firstOrNull()!!
    }

    override fun toDTO(it: User): UserDTO = UserDTO(
        id = it.id,
        discordId = it.discordId,
        minecraftUUID = it.minecraftUuid
    )
}