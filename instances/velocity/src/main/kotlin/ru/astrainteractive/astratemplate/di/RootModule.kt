package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astratemplate.core.di.CoreModule

interface RootModule {
    val velocityModule: VelocityModule
    val coreModule: CoreModule
}
