package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.filemanager.FileManager
import ru.astrainteractive.klibs.kdi.Module
import ru.astrainteractive.klibs.kdi.Reloadable

interface FilesModule : Module {
    val configFile: Reloadable<out FileManager>
}
