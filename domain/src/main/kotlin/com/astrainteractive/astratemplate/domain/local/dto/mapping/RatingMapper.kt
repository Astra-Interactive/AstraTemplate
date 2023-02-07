package com.astrainteractive.astratemplate.domain.local.dto.mapping

import com.astrainteractive.astratemplate.domain.local.dto.RatingDTO
import com.astrainteractive.astratemplate.domain.local.entities.UserRating
import ru.astrainteractive.astralibs.domain.mapping.Mapper

interface IRatingMapper : Mapper<UserRating, RatingDTO>
object RatingMapper : IRatingMapper {
    override fun fromDTO(it: RatingDTO): UserRating {
        throw NotImplementedError()
    }

    override fun toDTO(it: UserRating): RatingDTO = RatingDTO(
        id = it.id,
        userID = it.userID,
        reason = it.reason
    )
}
