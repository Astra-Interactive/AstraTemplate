package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.VelocityModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

class RootModuleImpl : RootModule {

    override val velocityModule: VelocityModule = VelocityModuleImpl()

    override val coreModule: CoreModule by Single {
        CoreModule.Default(velocityModule.dataDirectory.value.toFile())
    }
}
