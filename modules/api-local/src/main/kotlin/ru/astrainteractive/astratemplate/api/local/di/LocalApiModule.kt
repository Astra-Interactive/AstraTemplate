package ru.astrainteractive.astratemplate.api.local.di

import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapper
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapper
import ru.astrainteractive.klibs.kdi.Module

interface LocalApiModule : Module {
    val database: Database
    val ratingMapper: RatingMapper
    val userMapper: UserMapper
}
