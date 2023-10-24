package ru.astrainteractive.astratemplate.di

import CommandManager
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.local.LocalApi
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single

interface RootModule : Module {
    val servicesModule: ServicesModule

    val apiLocalModule: ApiLocalModule

    val apiRemoteModule: ApiRemoteModule

    val eventHandlerModule: Dependency<EventManager>

    val commandManager: Dependency<CommandManager>
}
