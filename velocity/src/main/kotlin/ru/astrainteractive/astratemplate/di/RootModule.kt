package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.filemanager.FileManager
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.plugin.Configuration
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single

interface RootModule : Module {
    val velocityModule: VelocityModule
    val logger: Single<Logger>
    val configurationFile: Single<FileManager>
    val configuration: Reloadable<Configuration>
}
