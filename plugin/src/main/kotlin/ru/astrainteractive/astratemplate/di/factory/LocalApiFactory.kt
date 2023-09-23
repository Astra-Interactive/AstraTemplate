package ru.astrainteractive.astratemplate.di.factory

import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.api.local.LocalApi
import ru.astrainteractive.astratemplate.api.local.LocalApi.Companion.LocalApi
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapper
import ru.astrainteractive.klibs.kdi.Factory

class LocalApiFactory(
    private val database: Database,
    private val ratingMapper: RatingMapper,
    private val userMapper: UserMapper
) : Factory<LocalApi> {
    override fun create(): LocalApi {
        return LocalApi(
            database = database,
            ratingMapper = ratingMapper,
            userMapper = userMapper
        )
    }
}
