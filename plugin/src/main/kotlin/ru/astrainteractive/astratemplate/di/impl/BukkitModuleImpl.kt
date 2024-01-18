package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Single

class BukkitModuleImpl : BukkitModule {
    override val plugin = Lateinit<AstraTemplate>()

    override val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }
    override val kyoriComponentSerializer: Single<KyoriComponentSerializer> = Single {
        KyoriComponentSerializer.Legacy
    }
}
