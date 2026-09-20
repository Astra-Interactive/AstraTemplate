package ru.astrainteractive.astratemplate.feature.command.di

import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astralibs.command.api.registrar.registerWhenReady
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.feature.command.additem.AddItemExecutor
import ru.astrainteractive.astratemplate.feature.command.additem.AddItemLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.feature.command.common.CommonLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.feature.command.damage.DamageLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.feature.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.feature.command.gui.GuiLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.feature.command.reload.ReloadLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.feature.command.rickmorty.RickMortyLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.feature.gui.di.GuiModule

class CommandModule(
    private val coreModule: CoreModule,
    guiModule: GuiModule,
    apiRemoteModule: ApiRemoteModule,
    lifecyclePlugin: Lifecycle,
    private val commandRegistrarContext: CommandRegistrarContext,
    multiplatformCommand: MultiplatformCommand
) {
    private val errorHandler = DefaultErrorHandler(
        multiplatformCommand = multiplatformCommand,
        translationKrate = coreModule.translationKrate,
        kyoriKrate = coreModule.kyoriKrate
    )

    private val nodes = listOf(
        AddItemLiteralArgumentBuilder(
            kyoriKrate = coreModule.kyoriKrate,
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler,
            executor = AddItemExecutor(
                translationKrate = coreModule.translationKrate,
                kyoriKrate = coreModule.kyoriKrate
            )
        ).create(),
        CommonLiteralArgumentBuilder(
            kyoriKrate = coreModule.kyoriKrate,
            translationKrate = coreModule.translationKrate,
            multiplatformCommand = multiplatformCommand
        ).create(),
        DamageLiteralArgumentBuilder(
            kyoriKrate = coreModule.kyoriKrate,
            translationKrate = coreModule.translationKrate,
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler
        ).create(),
        GuiLiteralArgumentBuilder(
            kyoriKrate = coreModule.kyoriKrate,
            multiplatformCommand = multiplatformCommand,
            router = guiModule.router,
            errorHandler = errorHandler
        ).create(),
        ReloadLiteralArgumentBuilder(
            kyoriKrate = coreModule.kyoriKrate,
            translationKrate = coreModule.translationKrate,
            lifecyclePlugin = lifecyclePlugin,
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler
        ).create(),
        RickMortyLiteralArgumentBuilder(
            scope = coreModule.ioScope,
            dispatchers = coreModule.dispatchers,
            rmApi = apiRemoteModule.rickMortyApi,
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler,
            translationKrate = coreModule.translationKrate,
            kyoriKrate = coreModule.kyoriKrate
        ).create()
    )

    val lifecycle: Lifecycle = Lifecycle.Lambda(
        onEnable = {
            commandRegistrarContext.registerWhenReady(nodes, coreModule.unconfinedScope)
        }
    )
}
