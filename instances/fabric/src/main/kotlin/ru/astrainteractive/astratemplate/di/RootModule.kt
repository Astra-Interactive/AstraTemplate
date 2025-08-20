package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule

interface RootModule {
    val lifecycle: Lifecycle

    val fabricModule: FabricModule

    val coreModule: CoreModule

    val apiLocalModule: ApiLocalModule

    val apiRemoteModule: ApiRemoteModule

    val commandModule: CommandModule

    class Default : RootModule {
        override val fabricModule: FabricModule by lazy {
            FabricModule.Default()
        }

        override val coreModule: CoreModule by lazy {
            CoreModule(
                dataFolder = fabricModule.configDir
            )
        }

        override val apiLocalModule: ApiLocalModule by lazy {
            ApiLocalModule(
                dataFolder = fabricModule.configDir,
                configFlow = coreModule.configKrate.cachedStateFlow,
                scope = coreModule.ioScope
            )
        }

        override val apiRemoteModule: ApiRemoteModule by lazy {
            ApiRemoteModule()
        }

        override val commandModule: CommandModule by lazy {
            CommandModule.Default(
                coreModule = coreModule,
                apiRemoteModule = apiRemoteModule
            )
        }

        private val lifecycles: List<Lifecycle>
            get() = listOf(
                commandModule.lifecycle
            )

        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onEnable = {
                    lifecycles.forEach(Lifecycle::onEnable)
                },
                onDisable = {
                    lifecycles.forEach(Lifecycle::onDisable)
                },
                onReload = {
                    lifecycles.forEach(Lifecycle::onReload)
                }
            )
        }
    }
}
