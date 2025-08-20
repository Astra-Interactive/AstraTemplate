package ru.astrainteractive.astratemplate.command.di

import kotlinx.coroutines.CoroutineScope
import ru.astrainteractive.astratemplate.command.additem.di.AddItemCommandDependencies
import ru.astrainteractive.astratemplate.command.common.di.CommonCommandsDependencies
import ru.astrainteractive.astratemplate.command.damage.di.DamageCommandDependencies
import ru.astrainteractive.astratemplate.command.gui.di.GuiCommandDependencies
import ru.astrainteractive.astratemplate.command.reload.di.ReloadCommandDependencies
import ru.astrainteractive.astratemplate.command.rickmorty.di.RickMortyCommandDependencies
import ru.astrainteractive.astratemplate.core.util.getValue
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.router.Router
import kotlin.random.Random

internal interface CommandManagerDependencies :
    AddItemCommandDependencies,
    DamageCommandDependencies,
    ReloadCommandDependencies,
    RickMortyCommandDependencies,
    CommonCommandsDependencies,
    GuiCommandDependencies {
    class Default(rootModule: RootModule) : CommandManagerDependencies {
        override val plugin = rootModule.bukkitModule.plugin
        override val translationKrate = rootModule.coreModule.translationKrate
        override val kyoriKrate = rootModule.bukkitModule.kyoriComponentSerializer
        override val translation by translationKrate
        override val kyori by kyoriKrate

        override val scope: CoroutineScope = rootModule.coreModule.ioScope
        override val rmApi = rootModule.apiRemoteModule.rickMortyApi
        override val dispatchers = rootModule.bukkitModule.dispatchers
        override fun getRabdomInt(): Int = Random.nextInt(1, 100)
        override val router: Router = rootModule.guiModule.router
    }
}
