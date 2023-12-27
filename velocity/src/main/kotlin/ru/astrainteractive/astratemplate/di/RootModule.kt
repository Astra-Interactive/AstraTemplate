package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astratemplate.shared.di.CoreModule
import ru.astrainteractive.klibs.kdi.Module

interface RootModule : Module {
    val velocityModule: VelocityModule
    val coreModule: CoreModule
}
