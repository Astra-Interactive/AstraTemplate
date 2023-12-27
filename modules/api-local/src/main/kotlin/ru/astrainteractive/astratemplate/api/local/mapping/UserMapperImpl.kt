package ru.astrainteractive.astratemplate.api.local.mapping

import ru.astrainteractive.astratemplate.api.local.entity.UserDAO
import ru.astrainteractive.astratemplate.api.local.model.UserModel
import ru.astrainteractive.klibs.mikro.core.domain.Mapper

internal interface UserMapper : Mapper<UserDAO, UserModel>

internal object UserMapperImpl : UserMapper {
    override fun fromDTO(it: UserModel): UserDAO {
        throw NotImplementedError()
    }

    override fun toDTO(it: UserDAO): UserModel = UserModel(
        id = it.id,
        discordId = it.discordId,
        minecraftUUID = it.minecraftUuid
    )
}
