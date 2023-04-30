package ru.astrainteractive.astratemplate.di

import CommandManager
import com.astrainteractive.astratemplate.api.local.LocalApi
import com.astrainteractive.astratemplate.api.remote.RickMortyApi
import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Reloadable
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.orm.Database
import ru.astrainteractive.astratemplate.events.EventManager
import ru.astrainteractive.astratemplate.plugin.CustomConfiguration
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation

interface RootModule : Module {
    val pluginModule: PluginModule
    val configurationModule: Reloadable<MainConfiguration>
    val customConfiguration: Reloadable<CustomConfiguration>
    val translationModule: Reloadable<Translation>
    val database: Single<Database>
    val rmApiModule: Dependency<RickMortyApi>
    val localApiModule: Dependency<LocalApi>
    val eventHandlerModule: Dependency<EventManager>
    val commandManager: Dependency<CommandManager>
}
