package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.klibs.kstorage.api.flow.StateFlowKrate
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultStateFlowMutableKrate
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

internal interface BukkitModule {
    val plugin: AstraTemplate
    val dispatchers: KotlinDispatchers
    val kyoriComponentSerializer: StateFlowKrate<KyoriComponentSerializer>

    class Default(override val plugin: AstraTemplate) : BukkitModule {
        override val dispatchers = DefaultBukkitDispatchers(plugin)
        override val kyoriComponentSerializer = DefaultStateFlowMutableKrate<KyoriComponentSerializer>(
            loader = { null },
            factory = { KyoriComponentSerializer.Legacy }
        )
    }
}
