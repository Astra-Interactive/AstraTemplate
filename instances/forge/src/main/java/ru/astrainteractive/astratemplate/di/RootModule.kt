package ru.astrainteractive.astratemplate.di

import kotlinx.coroutines.CoroutineDispatcher
import net.minecraftforge.fml.loading.FMLPaths
import ru.astrainteractive.astralibs.command.registrar.ForgeCommandRegistrarContext
import ru.astrainteractive.astralibs.coroutine.ForgeMainDispatcher
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.klibs.mikro.core.dispatchers.DefaultKotlinDispatchers
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers

class RootModule {

    val coreModule: CoreModule by lazy {
        CoreModule(
            dataFolder = FMLPaths.CONFIGDIR.get()
                .resolve("AstraTemplate")
                .toAbsolutePath()
                .toFile(),
            dispatchers = object : KotlinDispatchers by DefaultKotlinDispatchers {
                override val Main: CoroutineDispatcher = ForgeMainDispatcher
            }
        )
    }

    val apiLocalModule: ApiLocalModule by lazy {
        ApiLocalModule(
            dataFolder = coreModule.dataFolder,
            configFlow = coreModule.configKrate.cachedStateFlow,
            scope = coreModule.ioScope
        )
    }

    val apiRemoteModule: ApiRemoteModule by lazy {
        ApiRemoteModule()
    }

    val commandModule by lazy {
        CommandModule(
            commandRegistrarContext = ForgeCommandRegistrarContext(
                mainScope = coreModule.mainScope
            )
        )
    }

    val eventsModule: EventModule by lazy {
        EventModule(coreModule = coreModule)
    }

    private val lifecycles: List<Lifecycle>
        get() = listOf(
            eventsModule.lifecycle,
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
