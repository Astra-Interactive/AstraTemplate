package com.astrainteractive.astratemplate.api.local.mapping

import com.astrainteractive.astratemplate.api.dto.UserDTO
import com.astrainteractive.astratemplate.api.local.entities.UserDAO
import ru.astrainteractive.astralibs.domain.mapping.Mapper

interface UserMapper : Mapper<UserDAO, UserDTO>

object UserMapperImpl : UserMapper {
    override fun fromDTO(it: UserDTO): UserDAO {
        throw NotImplementedError()
    }

    override fun toDTO(it: UserDAO): UserDTO = UserDTO(
        id = it.id,
        discordId = it.discordId,
        minecraftUUID = it.minecraftUuid
    )
}
