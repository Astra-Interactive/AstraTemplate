package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Reloadable
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.filemanager.FileManager
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.plugin.Configuration

interface RootModule : Module {
    val velocityModule: VelocityModule
    val logger: Single<Logger>
    val configurationFile: Single<FileManager>
    val configuration: Reloadable<Configuration>
}
