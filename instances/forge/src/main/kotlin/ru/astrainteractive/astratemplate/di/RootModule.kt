package ru.astrainteractive.astratemplate.di

import net.minecraftforge.fml.loading.FMLPaths
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.brigadier.command.MinecraftMultiplatformCommands
import ru.astrainteractive.astralibs.command.registrar.ForgeCommandRegistrarContext
import ru.astrainteractive.astralibs.coroutines.MinecraftDispatchers
import ru.astrainteractive.astralibs.lifecycle.ForgeLifecycleServer
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.feature.command.di.CommandModule
import ru.astrainteractive.astratemplate.feature.event.di.di.EventModule
import ru.astrainteractive.astratemplate.feature.gui.di.StubGuiModule
import java.io.File

class RootModule(forgeLifecycleServer: ForgeLifecycleServer) {

    private val dataFolder = FMLPaths.CONFIGDIR.get()
        .resolve("AstraTemplate")
        .toAbsolutePath()
        .toFile()
        .also(File::mkdirs)
    private val coreModule: CoreModule = CoreModule(
        dataFolder = dataFolder,
        dispatchers = MinecraftDispatchers(),
        commandRegistrarContextFactory = ::ForgeCommandRegistrarContext
    )

    private val apiLocalModule: ApiLocalModule by lazy {
        ApiLocalModule(
            configFlow = coreModule.configKrate.cachedStateFlow,
            ioScope = coreModule.ioScope
        )
    }

    private val apiRemoteModule: ApiRemoteModule by lazy {
        ApiRemoteModule()
    }
    private val guiModule = StubGuiModule()

    private val commandModule by lazy {
        CommandModule(
            coreModule = coreModule,
            guiModule = guiModule,
            apiRemoteModule = apiRemoteModule,
            lifecyclePlugin = forgeLifecycleServer,
            commandRegistrarContext = coreModule.commandRegistrarContext,
            multiplatformCommand = MultiplatformCommand(MinecraftMultiplatformCommands())
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
            apiLocalModule.lifecycle
        )

    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda(
            onEnable = {
                lifecycles.forEach(Lifecycle::onEnable)
            },
            onDisable = {
                lifecycles.reversed().forEach(Lifecycle::onDisable)
            },
            onReload = {
                lifecycles.forEach(Lifecycle::onReload)
            }
        )
    }
}
