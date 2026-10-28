@file:Suppress("RootModuleConstruction")

package ru.astrainteractive.astratemplate.di

import net.minecraftforge.fml.loading.FMLPaths
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.brigadier.command.MinecraftMultiplatformCommands
import ru.astrainteractive.astralibs.command.registrar.ForgeCommandRegistrarContext
import ru.astrainteractive.astralibs.coroutines.MinecraftDispatchers
import ru.astrainteractive.astralibs.lifecycle.ForgeLifecycleServer
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.data.exposed.di.ExposedModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.gui.api.di.StubGuiModule
import ru.astrainteractive.astratemplate.integration.rickmorty.di.RickMortyModule
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

    private val exposedModule: ExposedModule by lazy {
        ExposedModule(
            configFlow = coreModule.configKrate.cachedStateFlow,
            ioScope = coreModule.ioScope
        )
    }

    private val rickMortyModule: RickMortyModule by lazy {
        RickMortyModule()
    }
    private val guiModule = StubGuiModule()

    private val commandModule by lazy {
        CommandModule(
            coreModule = coreModule,
            guiModule = guiModule,
            rickMortyModule = rickMortyModule,
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
            exposedModule.lifecycle
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
