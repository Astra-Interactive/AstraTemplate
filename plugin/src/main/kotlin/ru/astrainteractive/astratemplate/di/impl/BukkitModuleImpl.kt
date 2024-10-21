package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultStateFlowMutableKrate

internal class BukkitModuleImpl(override val plugin: AstraTemplate) : BukkitModule {

    override val dispatchers = DefaultBukkitDispatchers(plugin)
    override val kyoriComponentSerializer = DefaultStateFlowMutableKrate<KyoriComponentSerializer>(
        loader = { null },
        factory = { KyoriComponentSerializer.Legacy }
    )
}
