package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.klibs.kstorage.api.flow.StateFlowKrate
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

interface BukkitModule {
    val plugin: AstraTemplate
    val dispatchers: KotlinDispatchers
    val kyoriComponentSerializer: StateFlowKrate<KyoriComponentSerializer>
}
