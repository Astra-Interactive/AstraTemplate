package ru.astrainteractive.astratemplate.command.di

import kotlinx.coroutines.CoroutineScope
import ru.astrainteractive.astratemplate.command.additem.di.AddItemCommandDependencies
import ru.astrainteractive.astratemplate.command.common.di.CommonCommandsDependencies
import ru.astrainteractive.astratemplate.command.damage.di.DamageCommandDependencies
import ru.astrainteractive.astratemplate.command.gui.di.GuiCommandDependencies
import ru.astrainteractive.astratemplate.command.reload.di.ReloadCommandDependencies
import ru.astrainteractive.astratemplate.command.rickmorty.di.RickMortyCommandDependencies
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue
import kotlin.random.Random

interface CommandManagerDependencies :
    AddItemCommandDependencies,
    DamageCommandDependencies,
    ReloadCommandDependencies,
    RickMortyCommandDependencies,
    CommonCommandsDependencies,
    GuiCommandDependencies {
    class Default(rootModule: RootModule) : CommandManagerDependencies {
        override val plugin by rootModule.bukkitModule.plugin
        override val translation by rootModule.coreModule.translation
        override val kyoriComponentSerializer by rootModule.bukkitModule.kyoriComponentSerializer
        override val scope: CoroutineScope by rootModule.coreModule.pluginScope
        override val rmApi = rootModule.apiRemoteModule.rickMortyApi
        override val dispatchers by rootModule.bukkitModule.bukkitDispatchers
        override val randomIntProvider: Provider<Int> = Provider { Random.nextInt(1, 100) }
        override val router: Router by rootModule.guiModule.router
    }
}
