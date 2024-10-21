package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.gui.di.GuiModule

internal class RootModuleImpl(plugin: AstraTemplate) : RootModule {

    override val bukkitModule: BukkitModule = BukkitModuleImpl(plugin)

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
}
