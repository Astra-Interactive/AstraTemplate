package ru.astrainteractive.astratemplate.api.local.mapping

import ru.astrainteractive.astratemplate.api.local.entity.UserRatingDAO
import ru.astrainteractive.astratemplate.api.local.model.RatingModel
import ru.astrainteractive.klibs.mikro.core.domain.Mapper

internal interface RatingMapper : Mapper<UserRatingDAO, RatingModel>

internal object RatingMapperImpl : RatingMapper {
    override fun fromDTO(it: RatingModel): UserRatingDAO {
        throw NotImplementedError()
    }

    override fun toDTO(it: UserRatingDAO): RatingModel = RatingModel(
        id = it.id,
        userID = it.userID,
        reason = it.reason
    )
}
