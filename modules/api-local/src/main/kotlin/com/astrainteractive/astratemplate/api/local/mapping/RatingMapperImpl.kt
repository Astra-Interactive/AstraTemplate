package com.astrainteractive.astratemplate.api.local.mapping

import com.astrainteractive.astratemplate.api.dto.RatingDTO
import com.astrainteractive.astratemplate.api.local.entities.UserRatingDAO
import ru.astrainteractive.astralibs.domain.mapping.Mapper

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
