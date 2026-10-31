package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.command.api.brigadier.command.MultiplatformCommand
import ru.astrainteractive.astralibs.command.api.registrar.CommandRegistrarContext
import ru.astrainteractive.astralibs.command.api.registrar.registerWhenReady
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.additem.command.AddItemExecutor
import ru.astrainteractive.astratemplate.command.additem.command.AddItemLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.command.api.CommandErrorHandler
import ru.astrainteractive.astratemplate.command.damage.command.DamageLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.command.reload.command.ReloadLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.command.rickmorty.command.RickMortyLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.command.sample.command.GuiLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.command.translation.command.TranslationLiteralArgumentBuilder
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.gui.api.di.GuiModule

class CommandModule(
    private val coreModule: CoreModule,
    guiModule: GuiModule,
    fetchRandomCharacter: suspend (Int) -> Result<Any>,
    lifecyclePlugin: Lifecycle,
    private val commandRegistrarContext: CommandRegistrarContext,
    multiplatformCommand: MultiplatformCommand
) {
    private val errorHandler = CommandErrorHandler(
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
        TranslationLiteralArgumentBuilder(
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
            fetchRandomCharacter = fetchRandomCharacter,
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
