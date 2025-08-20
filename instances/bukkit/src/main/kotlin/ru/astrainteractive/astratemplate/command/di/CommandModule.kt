package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.additem.AddItemCommandRegistry
import ru.astrainteractive.astratemplate.command.common.CommonCommandsRegistry
import ru.astrainteractive.astratemplate.command.damage.DamageCommandRegistry
import ru.astrainteractive.astratemplate.command.gui.GuiCommandRegistry
import ru.astrainteractive.astratemplate.command.reload.ReloadCommandRegistry
import ru.astrainteractive.astratemplate.command.rickmorty.RandomRickAndMortyCommandRegistry
import ru.astrainteractive.astratemplate.di.RootModule

internal interface CommandModule {
    val lifecycle: Lifecycle

    class Default(rootModule: RootModule) : CommandModule {
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    val errorHandler = DefaultErrorHandler(
                        translationKrate = rootModule.coreModule.translationKrate,
                        kyoriKrate = rootModule.bukkitModule.kyoriKrate
                    )
                    AddItemCommandRegistry(
                        plugin = rootModule.bukkitModule.plugin,
                        kyoriKrate = rootModule.bukkitModule.kyoriKrate,
                        errorHandler = errorHandler
                    ).register()
                    CommonCommandsRegistry(
                        plugin = rootModule.bukkitModule.plugin,
                        kyoriKrate = rootModule.bukkitModule.kyoriKrate,
                        translationKrate = rootModule.coreModule.translationKrate,
                    ).register()
                    DamageCommandRegistry(
                        errorHandler = errorHandler,
                        plugin = rootModule.bukkitModule.plugin,
                        kyoriKrate = rootModule.bukkitModule.kyoriKrate,
                        translationKrate = rootModule.coreModule.translationKrate,
                    ).register()
                    GuiCommandRegistry(
                        errorHandler = errorHandler,
                        plugin = rootModule.bukkitModule.plugin,
                        kyoriKrate = rootModule.bukkitModule.kyoriKrate,
                        router = rootModule.guiModule.router,
                    ).register()
                    ReloadCommandRegistry(
                        errorHandler = errorHandler,
                        plugin = rootModule.bukkitModule.plugin,
                        kyoriKrate = rootModule.bukkitModule.kyoriKrate,
                        translationKrate = rootModule.coreModule.translationKrate,
                    ).register()
                    RandomRickAndMortyCommandRegistry(
                        scope = rootModule.coreModule.ioScope,
                        dispatchers = rootModule.bukkitModule.dispatchers,
                        rmApi = rootModule.apiRemoteModule.rickMortyApi,
                        errorHandler = errorHandler,
                        plugin = rootModule.bukkitModule.plugin,
                    ).register()
                }
            )
        }
    }
}
