package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.router.Router
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue
import kotlin.random.Random

interface CommandManagerDependencies {
    val plugin: AstraTemplate
    val translation: PluginTranslation
    val rmApi: RickMortyApi
    val pluginScope: AsyncComponent
    val dispatchers: BukkitDispatchers
    val randomIntProvider: Provider<Int>
    val bukkitTranslationContext: BukkitTranslationContext
    val router: Router

    class Default(rootModule: RootModule) : CommandManagerDependencies {
        override val plugin by rootModule.bukkitModule.plugin
        override val translation by rootModule.coreModule.translation
        override val rmApi = rootModule.apiRemoteModule.rickMortyApi
        override val pluginScope by rootModule.coreModule.pluginScope
        override val dispatchers by rootModule.bukkitModule.bukkitDispatchers
        override val randomIntProvider: Provider<Int> = Provider { Random.nextInt(1, 100) }

        override val bukkitTranslationContext by rootModule.bukkitModule.bukkitTranslationContext
        override val router: Router = rootModule.bukkitModule.router
    }
}
