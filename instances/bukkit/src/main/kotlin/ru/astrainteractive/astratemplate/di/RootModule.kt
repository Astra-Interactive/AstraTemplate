@file:Suppress("RootModuleConstruction")

package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.brigadier.command.PaperMultiplatformCommands
import ru.astrainteractive.astralibs.command.api.registrar.PaperCommandRegistrarContext
import ru.astrainteractive.astralibs.coroutines.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.data.exposed.di.ExposedModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.gui.bukkit.di.BukkitGuiModule
import ru.astrainteractive.astratemplate.integration.rickmorty.di.RickMortyModule

internal class RootModule(plugin: AstraTemplate) {

    val coreModule: CoreModule = CoreModule(
        dataFolder = plugin.dataFolder,
        dispatchers = DefaultBukkitDispatchers(plugin),
        commandRegistrarContextFactory = { mainScope ->
            PaperCommandRegistrarContext(
                mainScope = mainScope,
                plugin = plugin
            )
        }
    )

    val exposedModule: ExposedModule = ExposedModule(
        configFlow = coreModule.configKrate.cachedStateFlow,
        ioScope = coreModule.ioScope
    )

    val rickMortyModule: RickMortyModule = RickMortyModule()

    val eventModule: EventModule = EventModule(coreModule, plugin)
    val guiModule: BukkitGuiModule = BukkitGuiModule(
        coreModule = coreModule,
        userRepository = exposedModule.userRepository
    )
    val commandModule: CommandModule = CommandModule(
        coreModule = coreModule,
        rickMortyModule = rickMortyModule,
        guiModule = guiModule,
        lifecyclePlugin = plugin,
        commandRegistrarContext = coreModule.commandRegistrarContext,
        multiplatformCommand = MultiplatformCommand(PaperMultiplatformCommands())
    )

    private val lifecycles: List<Lifecycle>
        get() = listOf(
            coreModule.lifecycle,
            eventModule.lifecycle,
            exposedModule.lifecycle,
            commandModule.lifecycle
        )
    val lifecycle = Lifecycle.Lambda(
        onEnable = { lifecycles.forEach(Lifecycle::onEnable) },
        onDisable = { lifecycles.reversed().forEach(Lifecycle::onDisable) },
        onReload = { lifecycles.forEach(Lifecycle::onReload) }
    )
}
