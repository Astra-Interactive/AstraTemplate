package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.RootModule

class RootModuleImpl : RootModule {

    override val velocityModule = VelocityModuleImpl()

    override val coreModule: CoreModule by lazy {
        CoreModule(velocityModule.dataDirectory.toFile())
    }
}
