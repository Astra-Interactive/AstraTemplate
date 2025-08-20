package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultMutableKrate
import ru.astrainteractive.klibs.kstorage.util.asCachedKrate

class BukkitModule(val plugin: AstraTemplate) {
    val dispatchers = DefaultBukkitDispatchers(plugin)
    val kyoriKrate = DefaultMutableKrate<KyoriComponentSerializer>(
        loader = { null },
        factory = { KyoriComponentSerializer.Legacy }
    ).asCachedKrate()
}
