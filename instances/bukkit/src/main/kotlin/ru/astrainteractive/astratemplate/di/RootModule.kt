package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.gui.di.GuiModule


internal class RootModule(plugin: AstraTemplate) {

    val bukkitModule: BukkitModule = BukkitModule(plugin)

    val coreModule: CoreModule by lazy {
        CoreModule(bukkitModule.plugin.dataFolder)
    }

    val apiLocalModule: ApiLocalModule = ApiLocalModule(
        dataFolder = bukkitModule.plugin.dataFolder,
        configFlow = coreModule.configKrate.cachedStateFlow,
        scope = coreModule.ioScope
    )

    val apiRemoteModule: ApiRemoteModule = ApiRemoteModule()

    val eventModule: EventModule = EventModule(this)

    val commandModule: CommandModule = CommandModule.Default(this)

    val guiModule: GuiModule = GuiModule(
        coreModule = coreModule,
        bukkitModule = bukkitModule,
        apiLocalModule = apiLocalModule
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
