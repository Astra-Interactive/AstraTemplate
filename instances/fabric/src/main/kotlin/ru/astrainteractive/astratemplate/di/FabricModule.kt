package ru.astrainteractive.astratemplate.di

import net.fabricmc.loader.api.FabricLoader
import java.io.File

class FabricModule {
    val fabricLoader: FabricLoader
        get() = FabricLoader.getInstance()
    val configDir: File
        get() = fabricLoader.configDir.toFile()
}
