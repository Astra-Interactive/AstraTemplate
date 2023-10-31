package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.VelocityModule
import ru.astrainteractive.astratemplate.shared.di.SharedModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

class RootModuleImpl : RootModule {

    override val velocityModule: VelocityModule = VelocityModuleImpl()

    override val sharedModule: SharedModule by Single {
        SharedModule.Default(velocityModule.dataDirectory.value.toFile())
    }
}
