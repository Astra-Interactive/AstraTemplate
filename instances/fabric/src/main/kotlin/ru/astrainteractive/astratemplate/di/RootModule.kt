package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.klibs.mikro.core.dispatchers.DefaultKotlinDispatchers

class RootModule {
    val fabricModule: FabricModule by lazy {
        FabricModule()
    }

    val coreModule: CoreModule by lazy {
        CoreModule(
            dataFolder = fabricModule.configDir,
            dispatchers = DefaultKotlinDispatchers
        )
    }

    val apiLocalModule: ApiLocalModule by lazy {
        ApiLocalModule(
            dataFolder = fabricModule.configDir,
            configFlow = coreModule.configKrate.cachedStateFlow,
            scope = coreModule.ioScope
        )
    }

    val apiRemoteModule: ApiRemoteModule by lazy {
        ApiRemoteModule()
    }

    val commandModule: CommandModule by lazy {
        CommandModule(
            coreModule = coreModule,
            apiRemoteModule = apiRemoteModule
        )
    }

    private val lifecycles: List<Lifecycle>
        get() = listOf(
            commandModule.lifecycle
        )

    val lifecycle: Lifecycle by lazy {
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
