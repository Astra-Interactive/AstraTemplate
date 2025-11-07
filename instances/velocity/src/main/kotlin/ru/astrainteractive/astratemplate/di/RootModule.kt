package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astratemplate.core.di.CoreModule
class RootModule {
    val velocityModule = VelocityModule()

    val coreModule: CoreModule by lazy {
        CoreModule(velocityModule.dataDirectory.toFile())
    }
}
