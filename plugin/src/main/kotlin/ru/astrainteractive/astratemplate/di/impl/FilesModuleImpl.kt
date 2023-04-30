package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.di.FilesModule

internal object FilesModuleImpl : FilesModule {
    private val rootModule by RootModuleImpl
    private val pluginModule by rootModule.pluginModule

    override val configFile = Single {
        val plugin by pluginModule.plugin
        DefaultSpigotFileManager(plugin, "config.yml")
    }
}
