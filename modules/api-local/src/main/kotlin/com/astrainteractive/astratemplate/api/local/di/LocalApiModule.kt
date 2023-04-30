package com.astrainteractive.astratemplate.api.local.di

import com.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import com.astrainteractive.astratemplate.api.local.mapping.UserMapper
import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.orm.Database

interface LocalApiModule : Module {
    val database: Single<Database>
    val ratingMapper: Single<RatingMapper>
    val userMapper: Single<UserMapper>
}
