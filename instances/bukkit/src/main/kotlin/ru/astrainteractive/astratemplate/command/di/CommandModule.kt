package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.command.api.registrar.PaperCommandRegistrarContext
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.additem.AddItemCommandRegistry
import ru.astrainteractive.astratemplate.command.additem.AddItemExecutor
import ru.astrainteractive.astratemplate.command.common.CommonCommandsRegistry
import ru.astrainteractive.astratemplate.command.damage.DamageCommandRegistry
import ru.astrainteractive.astratemplate.command.errorhandler.DefaultErrorHandler
import ru.astrainteractive.astratemplate.command.gui.GuiCommandRegistry
import ru.astrainteractive.astratemplate.command.reload.ReloadCommandRegistry
import ru.astrainteractive.astratemplate.command.rickmorty.RickMortyCommandRegistrar
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.gui.di.GuiModule

internal class CommandModule(
    coreModule: CoreModule,
    bukkitModule: BukkitModule,
    guiModule: GuiModule,
    apiRemoteModule: ApiRemoteModule,
) {
    private val commandRegistrarContext = PaperCommandRegistrarContext(
        coreModule.mainScope,
        bukkitModule.plugin
    )
    private val nodes = buildList {
        val errorHandler = DefaultErrorHandler(
            translationKrate = coreModule.translationKrate,
            kyoriKrate = bukkitModule.kyoriKrate
        )
        AddItemCommandRegistry(
            kyoriKrate = bukkitModule.kyoriKrate,
            errorHandler = errorHandler,
            executor = AddItemExecutor()
        ).createNode().run(::add)
        CommonCommandsRegistry(
            kyoriKrate = bukkitModule.kyoriKrate,
            translationKrate = coreModule.translationKrate,
        ).createNode().run(::add)
        DamageCommandRegistry(
            errorHandler = errorHandler,
            kyoriKrate = bukkitModule.kyoriKrate,
            translationKrate = coreModule.translationKrate,
        ).createNode().run(::add)
        GuiCommandRegistry(
            router = guiModule.router,
            kyoriKrate = bukkitModule.kyoriKrate,
            errorHandler = errorHandler,
        ).createNode().run(::add)
        ReloadCommandRegistry(
            plugin = bukkitModule.plugin,
            translationKrate = coreModule.translationKrate,
            kyoriKrate = bukkitModule.kyoriKrate,
            errorHandler = errorHandler,
        ).createNode().run(::add)
        RickMortyCommandRegistrar(
            scope = coreModule.ioScope,
            dispatchers = coreModule.dispatchers,
            rmApi = apiRemoteModule.rickMortyApi,
            errorHandler = errorHandler,
        ).createNode().run(::add)
    }
    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda(
            onEnable = {
                nodes.forEach(commandRegistrarContext::registerWhenReady)
            }
        )
    }
}
