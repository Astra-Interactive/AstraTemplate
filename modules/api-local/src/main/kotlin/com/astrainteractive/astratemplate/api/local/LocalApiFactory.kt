package com.astrainteractive.astratemplate.api.local

import com.astrainteractive.astratemplate.api.local.mapping.RatingMapperImpl
import com.astrainteractive.astratemplate.api.local.mapping.UserMapperImpl
import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.astralibs.orm.Database

class LocalApiFactory(
    private val database: Database
) : Factory<LocalApi>() {
    override fun initializer(): LocalApi {
        return LocalApiImpl(
            database = database,
            ratingMapper = RatingMapperImpl,
            userMapper = UserMapperImpl
        )
    }
}
