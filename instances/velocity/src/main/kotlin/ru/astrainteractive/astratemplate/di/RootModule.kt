package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.klibs.mikro.core.dispatchers.DefaultKotlinDispatchers

class RootModule {
    val velocityModule = VelocityModule()

    val coreModule: CoreModule by lazy {
        CoreModule(
            dataFolder = velocityModule.dataDirectory.toFile(),
            dispatchers = DefaultKotlinDispatchers
        )
    }
}
