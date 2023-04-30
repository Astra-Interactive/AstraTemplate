package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astralibs.Module
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager

object FilesModule : Module {
    val configFile = Single {
        DefaultSpigotFileManager(RootModule.plugin.value, "config.yml")
    }
}
