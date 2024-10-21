package ru.astrainteractive.astratemplate.di

import net.fabricmc.loader.api.FabricLoader
import java.io.File

interface FabricModule {
    val fabricLoader: FabricLoader
    val configDir: File

    class Default : FabricModule {
        override val fabricLoader: FabricLoader
            get() = FabricLoader.getInstance()
        override val configDir: File
            get() = fabricLoader.configDir.toFile()
    }
}
