package ru.astrainteractive.astratemplate.feature.command.di

import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astralibs.command.api.registrar.registerWhenReady
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
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
import ru.astrainteractive.astratemplate.integration.rickmorty.di.RickMortyModule

class CommandModule(
    private val coreModule: CoreModule,
    guiModule: GuiModule,
    rickMortyModule: RickMortyModule,
    lifecyclePlugin: Lifecycle,
    private val commandRegistrarContext: CommandRegistrarContext,
    multiplatformCommand: MultiplatformCommand
) {
    private val errorHandler = DefaultErrorHandler(
        multiplatformCommand = multiplatformCommand,
        translationKrate = coreModule.translationKrate
    )

    private val nodes = listOf(
        AddItemLiteralArgumentBuilder(
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler,
            executor = AddItemExecutor(
                translationKrate = coreModule.translationKrate
            )
        ).create(),
        CommonLiteralArgumentBuilder(
            translationKrate = coreModule.translationKrate,
            multiplatformCommand = multiplatformCommand
        ).create(),
        DamageLiteralArgumentBuilder(
            translationKrate = coreModule.translationKrate,
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler
        ).create(),
        GuiLiteralArgumentBuilder(
            multiplatformCommand = multiplatformCommand,
            router = guiModule.router,
            errorHandler = errorHandler
        ).create(),
        ReloadLiteralArgumentBuilder(
            translationKrate = coreModule.translationKrate,
            lifecyclePlugin = lifecyclePlugin,
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler
        ).create(),
        RickMortyLiteralArgumentBuilder(
            scope = coreModule.ioScope,
            dispatchers = coreModule.dispatchers,
            rmApi = rickMortyModule.rickMortyApi,
            multiplatformCommand = multiplatformCommand,
            errorHandler = errorHandler,
            translationKrate = coreModule.translationKrate
        ).create()
    )

    val lifecycle: Lifecycle = Lifecycle.Lambda(
        onEnable = {
            commandRegistrarContext.registerWhenReady(nodes, coreModule.unconfinedScope)
        }
    )
}
