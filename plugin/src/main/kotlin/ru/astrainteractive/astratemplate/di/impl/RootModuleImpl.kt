package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.di.BukkitModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.gui.di.GuiModule
import ru.astrainteractive.klibs.kdi.getValue
import java.io.File

internal class RootModuleImpl : RootModule {

    override val bukkitModule: BukkitModule by lazy {
        BukkitModuleImpl()
    }

    override val apiLocalModule: ApiLocalModule by lazy {
        ApiLocalModule.Default("${bukkitModule.plugin.value.dataFolder}${File.separator}data.db")
    }

    override val apiRemoteModule: ApiRemoteModule by lazy {
        ApiRemoteModule.Default()
    }

    override val coreModule: CoreModule by lazy {
        CoreModule.Default(bukkitModule.plugin.value.dataFolder)
    }

    override val eventModule: EventModule by lazy {
        EventModule.Default(this)
    }

    override val commandModule: CommandModule by lazy {
        CommandModule.Default(this)
    }

    override val guiModule: GuiModule by lazy {
        GuiModule.Default(
            coreModule = coreModule,
            bukkitModule = bukkitModule,
            apiLocalModule = apiLocalModule
        )
    }
}
