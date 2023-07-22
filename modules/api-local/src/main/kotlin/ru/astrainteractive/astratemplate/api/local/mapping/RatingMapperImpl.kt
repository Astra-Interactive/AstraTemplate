package ru.astrainteractive.astratemplate.api.local.mapping

import ru.astrainteractive.astratemplate.api.dto.RatingDTO
import ru.astrainteractive.astratemplate.api.local.entities.UserRatingDAO
import ru.astrainteractive.klibs.mikro.core.domain.Mapper

interface RatingMapper : Mapper<UserRatingDAO, RatingDTO>
object RatingMapperImpl : RatingMapper {
    override fun fromDTO(it: RatingDTO): UserRatingDAO {
        throw NotImplementedError()
    }

    override fun toDTO(it: UserRatingDAO): RatingDTO = RatingDTO(
        id = it.id,
        userID = it.userID,
        reason = it.reason
    )
}
