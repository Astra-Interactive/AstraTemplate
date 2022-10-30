package com.astrainteractive.astratemplate.domain.local.dto.mapping

import com.astrainteractive.astratemplate.domain.local.dto.RatingDTO
import com.astrainteractive.astratemplate.domain.local.entities.RatingRelationTable
import com.astrainteractive.astratemplate.domain.local.entities.UserRating
import ru.astrainteractive.astralibs.domain.mapping.IMapper

interface IRatingMapper : IMapper<UserRating, RatingDTO>
object RatingMapper : IRatingMapper {
    override fun fromDTO(it: RatingDTO): UserRating {
        return RatingRelationTable.find(constructor = ::UserRating) {
            RatingRelationTable.id.eq(it.id)
        }.firstOrNull()!!
    }

    override fun toDTO(it: UserRating): RatingDTO = RatingDTO(
        id = it.id,
        userID = it.userID,
        reason = it.reason
    )
}