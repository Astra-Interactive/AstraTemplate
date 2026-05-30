package ru.astrainteractive.astratemplate.feature.command.di

import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.feature.command.additem.AddItemCommandRegistry
import ru.astrainteractive.astratemplate.feature.command.additem.AddItemExecutor
import ru.astrainteractive.astratemplate.feature.command.common.CommonCommandsRegistry
import ru.astrainteractive.astratemplate.feature.command.damage.DamageCommandRegistry
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.feature.command.gui.GuiCommandRegistry
import ru.astrainteractive.astratemplate.feature.command.reload.ReloadCommandRegistry
import ru.astrainteractive.astratemplate.feature.command.rickmorty.RickMortyCommandRegistrar
import ru.astrainteractive.astratemplate.feature.gui.di.GuiModule

class CommandModule(
    private val coreModule: CoreModule,
    private val guiModule: GuiModule,
    private val apiRemoteModule: ApiRemoteModule,
    private val lifecyclePlugin: Lifecycle,
    private val commandRegistrarContext: CommandRegistrarContext,
    private val multiplatformCommand: MultiplatformCommand
) {
    private val errorHandler by lazy {
        DefaultErrorHandler(
            multiplatformCommand = multiplatformCommand,
            translationKrate = coreModule.translationKrate,
            kyoriKrate = coreModule.kyoriKrate
        )
    }

    val lifecycle: Lifecycle = Lifecycle.Lambda(
        onEnable = {
            AddItemCommandRegistry(
                kyoriKrate = coreModule.kyoriKrate,
                registrarContext = commandRegistrarContext,
                multiplatformCommand = multiplatformCommand,
                errorHandler = errorHandler,
                executor = AddItemExecutor()
            ).register()
            CommonCommandsRegistry(
                kyoriKrate = coreModule.kyoriKrate,
                translationKrate = coreModule.translationKrate,
                registrarContext = commandRegistrarContext,
                multiplatformCommand = multiplatformCommand
            ).register()
            DamageCommandRegistry(
                kyoriKrate = coreModule.kyoriKrate,
                translationKrate = coreModule.translationKrate,
                registrarContext = commandRegistrarContext,
                multiplatformCommand = multiplatformCommand,
                errorHandler = errorHandler
            ).register()
            GuiCommandRegistry(
                kyoriKrate = coreModule.kyoriKrate,
                registrarContext = commandRegistrarContext,
                multiplatformCommand = multiplatformCommand,
                router = guiModule.router,
                errorHandler = errorHandler
            ).register()
            ReloadCommandRegistry(
                kyoriKrate = coreModule.kyoriKrate,
                translationKrate = coreModule.translationKrate,
                lifecyclePlugin = lifecyclePlugin,
                registrarContext = commandRegistrarContext,
                multiplatformCommand = multiplatformCommand,
                errorHandler = errorHandler
            ).register()
            RickMortyCommandRegistrar(
                scope = coreModule.ioScope,
                dispatchers = coreModule.dispatchers,
                rmApi = apiRemoteModule.rickMortyApi,
                registrarContext = commandRegistrarContext,
                multiplatformCommand = multiplatformCommand,
                errorHandler = errorHandler
            ).register()
        }
    )
}
