package ru.astrainteractive.astratemplate.di

import CommandManager
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Module

interface RootModule : Module {
    val bukkitModule: BukkitModule

    val apiLocalModule: ApiLocalModule

    val apiRemoteModule: ApiRemoteModule

    val eventHandlerModule: Dependency<EventManager>

    val commandManager: Dependency<CommandManager>
}
