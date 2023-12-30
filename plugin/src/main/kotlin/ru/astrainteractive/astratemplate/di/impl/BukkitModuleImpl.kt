package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.router.DefaultRouter
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

class BukkitModuleImpl(rootModule: RootModule) : BukkitModule {
    override val plugin = Lateinit<AstraTemplate>()

    override val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }
    override val kyoriComponentSerializer: Single<KyoriComponentSerializer> = Single {
        KyoriComponentSerializer.Legacy
    }
    override val bukkitTranslationContext: Single<BukkitTranslationContext> = Single {
        BukkitTranslationContext.Default { kyoriComponentSerializer.value }
    }
    override val router: Router by Single {
        DefaultRouter(
            rootModule = rootModule,
            scope = rootModule.coreModule.pluginScope.value,
            dispatchers = bukkitDispatchers.value
        )
    }
}
