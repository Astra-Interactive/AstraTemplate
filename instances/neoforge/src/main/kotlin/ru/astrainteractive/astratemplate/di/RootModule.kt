@file:Suppress("RootModuleConstruction")

package ru.astrainteractive.astratemplate.di

import net.neoforged.fml.loading.FMLPaths
import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.brigadier.command.MinecraftMultiplatformCommands
import ru.astrainteractive.astralibs.command.registrar.NeoForgeCommandRegistrarContext
import ru.astrainteractive.astralibs.coroutines.MinecraftDispatchers
import ru.astrainteractive.astralibs.lifecycle.ForgeLifecycleServer
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.data.exposed.di.ExposedModule
import ru.astrainteractive.astratemplate.event.neoforge.di.EventModule
import ru.astrainteractive.astratemplate.gui.api.di.StubGuiModule
import ru.astrainteractive.astratemplate.integration.rickmorty.di.RickMortyModule
import java.io.File

internal class RootModule(forgeLifecycleServer: ForgeLifecycleServer) {
    private val dataFolder = FMLPaths.CONFIGDIR.get()
        .resolve("AstraTemplate")
        .toAbsolutePath()
        .toFile()
        .also(File::mkdirs)
    private val coreModule: CoreModule = CoreModule(
        dataFolder = dataFolder,
        dispatchers = MinecraftDispatchers(),
        commandRegistrarContextFactory = ::NeoForgeCommandRegistrarContext
    )

    val exposedModule: ExposedModule by lazy {
        ExposedModule(
            configFlow = coreModule.configKrate.cachedStateFlow,
            ioScope = coreModule.ioScope
        )
    }

    val rickMortyModule: RickMortyModule by lazy {
        RickMortyModule()
    }
    private val guiModule = StubGuiModule()
    private val commandModule by lazy {
        CommandModule(
            coreModule = coreModule,
            guiModule = guiModule,
            fetchRandomCharacter = rickMortyModule.characterRepository::fetchRandomCharacter,
            lifecyclePlugin = forgeLifecycleServer,
            commandRegistrarContext = coreModule.commandRegistrarContext,
            multiplatformCommand = MultiplatformCommand(MinecraftMultiplatformCommands())
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
