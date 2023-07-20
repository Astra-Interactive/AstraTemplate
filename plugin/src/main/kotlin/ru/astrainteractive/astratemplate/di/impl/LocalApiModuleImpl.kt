package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.api.local.di.LocalApiModule
import ru.astrainteractive.astratemplate.api.local.mapping.RatingMapperImpl
import ru.astrainteractive.astratemplate.api.local.mapping.UserMapperImpl
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

internal class LocalApiModuleImpl(
    rootModule: RootModule
) : LocalApiModule {

    override val database by rootModule.database

    override val ratingMapper by Single {
        RatingMapperImpl
    }
    override val userMapper by Single {
        UserMapperImpl
    }
}
