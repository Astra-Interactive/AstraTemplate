package com.astrainteractive.astratemplate.domain.local.dto.mapping

import com.astrainteractive.astratemplate.domain.local.dto.UserDTO
import com.astrainteractive.astratemplate.domain.local.entities.User
import ru.astrainteractive.astralibs.domain.mapping.Mapper

interface IUserMapper : Mapper<User, UserDTO>

object UserMapper : IUserMapper {
    override fun fromDTO(it: UserDTO): User {
        throw NotImplementedError()
    }

    override fun toDTO(it: User): UserDTO = UserDTO(
        id = it.id,
        discordId = it.discordId,
        minecraftUUID = it.minecraftUuid
    )
}
