package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.command.di.CommandModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.astratemplate.gui.di.GuiModule
import ru.astrainteractive.klibs.kdi.Module

interface RootModule : Module {
    val bukkitModule: BukkitModule

    val apiLocalModule: ApiLocalModule

    val apiRemoteModule: ApiRemoteModule

    val coreModule: CoreModule

    val eventModule: EventModule

    val commandModule: CommandModule

    val guiModule: GuiModule
}
