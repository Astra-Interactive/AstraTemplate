package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.astratemplate.di.FilesModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

internal class FilesModuleImpl(
    private val pluginModule: RootModule
) : FilesModule {
    override val configFile = Single {
        val plugin by pluginModule.plugin
        DefaultSpigotFileManager(plugin, "config.yml")
    }
}
