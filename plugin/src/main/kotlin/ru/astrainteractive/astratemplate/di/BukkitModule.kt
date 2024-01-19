package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Single

interface BukkitModule {
    val plugin: Lateinit<AstraTemplate>
    val bukkitDispatchers: Dependency<BukkitDispatchers>
    val kyoriComponentSerializer: Single<KyoriComponentSerializer>
}
