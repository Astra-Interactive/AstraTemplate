package ru.astrainteractive.astratemplate.di

import CommandManager
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.local.LocalApi
import ru.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.astratemplate.plugin.CustomConfiguration
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single

interface RootModule : Module {
    val plugin: Lateinit<AstraTemplate>
    val logger: Dependency<Logger>
    val bukkitDispatchers: Dependency<BukkitDispatchers>
    val filesModule: FilesModule
    val pluginScope: Dependency<AsyncComponent>
    val configurationModule: Reloadable<MainConfiguration>
    val customConfiguration: Reloadable<CustomConfiguration>
    val translation: Reloadable<Translation>
    val database: Single<Database>
    val rmApiModule: Dependency<RickMortyApi>
    val localApiModule: Dependency<LocalApi>
    val eventHandlerModule: Dependency<EventManager>
    val commandManager: Dependency<CommandManager>
}
