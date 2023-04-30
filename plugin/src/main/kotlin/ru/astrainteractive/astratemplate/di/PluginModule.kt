package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.AstraTemplate

interface PluginModule : Module {
    val plugin: Dependency<AstraTemplate>
    val logger: Dependency<Logger>
    val bukkitDispatchers: Dependency<BukkitDispatchers>
    val pluginScope: Dependency<AsyncComponent>
}
