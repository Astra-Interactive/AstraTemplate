package ru.astrainteractive.astratemplate.di

import java.io.File
import net.minecraftforge.fml.loading.FMLPaths
import ru.astrainteractive.astralibs.command.registrar.ForgeCommandRegistrarContext
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
    private val commandRegistrarContext = ForgeCommandRegistrarContext(coreModule.mainScope)

    private val apiLocalModule: ApiLocalModule by lazy {
        ApiLocalModule(
            configFlow = coreModule.configKrate.cachedStateFlow,
            ioScope = coreModule.ioScope
        )
    }

    private val apiRemoteModule: ApiRemoteModule by lazy {
        ApiRemoteModule()
    }

    private val commandModule by lazy {
        CommandModule(
            commandRegistrarContext = commandRegistrarContext
        )
    }

    val eventsModule by lazy {
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
