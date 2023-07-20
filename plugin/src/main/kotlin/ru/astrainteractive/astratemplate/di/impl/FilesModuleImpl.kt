package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.astratemplate.di.FilesModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

internal class FilesModuleImpl(
    private val rootModule: RootModule
) : FilesModule {
    override val configFile = Single {
        val plugin by rootModule.plugin
        DefaultSpigotFileManager(plugin, "config.yml")
    }
}
