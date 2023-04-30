package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.filemanager.SpigotFileManager

interface FilesModule : Module {
    val configFile: Single<SpigotFileManager>
}
