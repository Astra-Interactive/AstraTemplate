package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.filemanager.SpigotFileManager
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Single

interface FilesModule : Module {
    val configFile: Single<SpigotFileManager>
}
