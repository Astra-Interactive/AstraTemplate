package ru.astrainteractive.astratemplate.di

import java.io.File
import net.neoforged.fml.loading.FMLPaths
import ru.astrainteractive.astralibs.command.registrar.NeoForgeCommandRegistrarContext
import ru.astrainteractive.astralibs.coroutines.MinecraftDispatchers
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.feature.event.di.di.EventModule

class RootModule {
    private val dataFolder = FMLPaths.CONFIGDIR.get()
        .resolve("AspeKt")
        .toAbsolutePath()
        .toFile()
        .also(File::mkdirs)
    private val coreModule: CoreModule = CoreModule(
        dataFolder = dataFolder,
        dispatchers = MinecraftDispatchers()
    )
    private val commandRegistrarContext = NeoForgeCommandRegistrarContext(coreModule.mainScope)

    val apiLocalModule: ApiLocalModule by lazy {
        ApiLocalModule(
            configFlow = coreModule.configKrate.cachedStateFlow,
            ioScope = coreModule.ioScope
        )
    }

    val apiRemoteModule: ApiRemoteModule by lazy {
        ApiRemoteModule()
    }


    private val commandModule by lazy {
        CommandModule(
            commandRegistrarContext = commandRegistrarContext
        )
    }

    private val eventsModule by lazy {
        EventModule(coreModule = coreModule)
    }

    private val lifecycles: List<Lifecycle>
        get() = listOf(
            coreModule.lifecycle,
            eventsModule.lifecycle,
            commandModule.lifecycle,
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
