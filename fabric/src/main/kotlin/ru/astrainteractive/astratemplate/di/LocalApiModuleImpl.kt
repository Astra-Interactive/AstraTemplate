package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astratemplate.api.local.di.LocalApiModule
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapperImpl
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapperImpl
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

object LocalApiModuleImpl : LocalApiModule {
    override val database by RootModule.databaseModule
    override val ratingMapper by Single { RatingMapperImpl }
    override val userMapper by Single { UserMapperImpl }
}
