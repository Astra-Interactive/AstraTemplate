package ru.astrainteractive.astratemplate.di.impl

import com.astrainteractive.astratemplate.api.local.di.LocalApiModule
import com.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import com.astrainteractive.astratemplate.api.local.mapping.RatingMapperImpl
import com.astrainteractive.astratemplate.api.local.mapping.UserMapper
import com.astrainteractive.astratemplate.api.local.mapping.UserMapperImpl
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.orm.Database

internal object LocalApiModuleImpl : LocalApiModule {

    override val database: Single<Database> = RootModuleImpl.database

    override val ratingMapper: Single<RatingMapper> = Single {
        RatingMapperImpl
    }
    override val userMapper: Single<UserMapper> = Single {
        UserMapperImpl
    }
}
