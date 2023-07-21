package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.filemanager.impl.JVMFileManager
import ru.astrainteractive.astratemplate.di.FilesModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.getValue

internal class FilesModuleImpl(
    private val rootModule: RootModule
) : FilesModule {
    override val configFile = Reloadable {
        val plugin by rootModule.plugin
        JVMFileManager("config.yml", plugin.dataFolder)
    }
}
