package ru.astrainteractive.astratemplate.di.factory

import kotlinx.serialization.encodeToString
import org.bukkit.plugin.java.JavaPlugin
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.filemanager.impl.JVMFileManager
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.getValue

class MainConfigurationFactory(
    private val plugin: JavaPlugin,
    private val configLoader: ConfigLoader
) : Factory<MainConfiguration> {
    override fun create(): MainConfiguration {
        val configFile = JVMFileManager("config.yml", plugin.dataFolder)
        val configuration = configLoader.toClassOrDefault(
            configFile.configFile,
            ::MainConfiguration
        )
        if (!configFile.configFile.exists()) {
            configFile.configFile.createNewFile()
            configFile.configFile.writeText(configLoader.yaml.encodeToString(configuration))
        }
        return configuration
    }
}