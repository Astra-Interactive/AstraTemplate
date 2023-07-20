package ru.astrainteractive.astratemplate.api.local.mapping

import ru.astrainteractive.astralibs.domain.mapping.Mapper
import ru.astrainteractive.astratemplate.api.dto.UserDTO
import ru.astrainteractive.astratemplate.api.local.entities.UserDAO

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
