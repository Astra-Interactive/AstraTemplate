package ru.astrainteractive.astratemplate.di

import CommandManager
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.serialization.KyoriComponentSerializer
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Single

interface BukkitModule {
    val plugin: Lateinit<AstraTemplate>
    val bukkitDispatchers: Dependency<BukkitDispatchers>
    val kyoriComponentSerializer: Single<KyoriComponentSerializer>
    val commandManager: Single<CommandManager>
    val bukkitTranslationContext: Single<BukkitTranslationContext>
    val router: Router
}
