package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.brigadier.command.PaperMultiplatformCommands
import ru.astrainteractive.astralibs.command.api.registrar.PaperCommandRegistrarContext
import ru.astrainteractive.astralibs.coroutines.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.feature.command.di.CommandModule
import ru.astrainteractive.astratemplate.feature.event.di.EventModule
import ru.astrainteractive.astratemplate.feature.gui.di.BukkitGuiModule

internal class RootModule(plugin: AstraTemplate) {

    val coreModule: CoreModule = CoreModule(
        dataFolder = plugin.dataFolder,
        dispatchers = DefaultBukkitDispatchers(plugin)
    )

    val apiLocalModule: ApiLocalModule = ApiLocalModule(
        configFlow = coreModule.configKrate.cachedStateFlow,
        ioScope = coreModule.ioScope
    )

    val apiRemoteModule: ApiRemoteModule = ApiRemoteModule()

    val eventModule: EventModule = EventModule(coreModule, plugin)
    val guiModule: BukkitGuiModule = BukkitGuiModule(
        coreModule = coreModule,
        apiLocalModule = apiLocalModule
    )
    val commandModule: CommandModule = CommandModule(
        coreModule = coreModule,
        apiRemoteModule = apiRemoteModule,
        guiModule = guiModule,
        lifecyclePlugin = plugin,
        commandRegistrarContext = PaperCommandRegistrarContext(
            mainScope = coreModule.mainScope,
            plugin = plugin
        ),
        multiplatformCommand = MultiplatformCommand(PaperMultiplatformCommands())
    )

    private val lifecycles: List<Lifecycle>
        get() = listOf(
            coreModule.lifecycle,
            eventModule.lifecycle,
            apiLocalModule.lifecycle,
            commandModule.lifecycle
        )
    val lifecycle = Lifecycle.Lambda(
        onEnable = { lifecycles.forEach(Lifecycle::onEnable) },
        onDisable = { lifecycles.forEach(Lifecycle::onDisable) },
        onReload = { lifecycles.forEach(Lifecycle::onReload) }
    )
}
