package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single

interface ServicesModule {
    val plugin: Lateinit<AstraTemplate>
    val configLoader: Single<ConfigLoader>
    val logger: Dependency<Logger>
    val bukkitDispatchers: Dependency<BukkitDispatchers>
    val pluginScope: Dependency<AsyncComponent>
    val translation: Reloadable<Translation>
    val configurationModule: Reloadable<MainConfiguration>
}