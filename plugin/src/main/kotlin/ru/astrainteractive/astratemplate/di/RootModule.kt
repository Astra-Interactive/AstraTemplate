package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.gui.di.GuiModule

internal interface RootModule {
    val lifecycle: Lifecycle

    val bukkitModule: BukkitModule

    val apiLocalModule: ApiLocalModule

    val apiRemoteModule: ApiRemoteModule

    val coreModule: CoreModule

    val eventModule: EventModule

    val commandModule: CommandModule

    val guiModule: GuiModule

    class Default(plugin: AstraTemplate) : RootModule {

        override val bukkitModule: BukkitModule = BukkitModule.Default(plugin)

        override val coreModule: CoreModule by lazy {
            CoreModule.Default(bukkitModule.plugin.dataFolder)
        }

        override val apiLocalModule: ApiLocalModule = ApiLocalModule.Default(
            dataFolder = bukkitModule.plugin.dataFolder,
            configFlow = coreModule.configurationModule.cachedStateFlow,
            scope = coreModule.pluginScope
        )

        override val apiRemoteModule: ApiRemoteModule = ApiRemoteModule.Default()

        override val eventModule: EventModule = EventModule.Default(this)

        override val commandModule: CommandModule = CommandModule.Default(this)

        override val guiModule: GuiModule = GuiModule.Default(
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
        override val lifecycle = Lifecycle.Lambda(
            onEnable = { lifecycles.forEach(Lifecycle::onEnable) },
            onDisable = { lifecycles.forEach(Lifecycle::onDisable) },
            onReload = { lifecycles.forEach(Lifecycle::onReload) }
        )
    }
}
