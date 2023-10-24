package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Lateinit

interface BukkitModule {
    val plugin: Lateinit<AstraTemplate>
    val bukkitDispatchers: Dependency<BukkitDispatchers>
}