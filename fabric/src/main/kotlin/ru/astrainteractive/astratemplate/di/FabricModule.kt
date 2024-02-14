package ru.astrainteractive.astratemplate.di

import net.fabricmc.loader.api.FabricLoader
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue
import java.io.File

interface FabricModule {
    val fabricLoader: FabricLoader
    val configDir: File

    class Default : FabricModule {
        override val fabricLoader: FabricLoader by Provider {
            FabricLoader.getInstance()
        }
        override val configDir: File by Provider {
            fabricLoader.configDir.toFile()
        }
    }
}
