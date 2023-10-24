package ru.astrainteractive.astratemplate.shared.di.factory

import java.io.File
import kotlinx.serialization.encodeToString
import ru.astrainteractive.astralibs.filemanager.impl.JVMFileManager
import ru.astrainteractive.astralibs.serialization.YamlSerializer
import ru.astrainteractive.astratemplate.shared.core.MainConfiguration
import ru.astrainteractive.klibs.kdi.Factory

class MainConfigurationFactory(
    private val dataFolder: File,
    private val yamlSerializer: YamlSerializer
) : Factory<MainConfiguration> {
    override fun create(): MainConfiguration {
        val configFile = JVMFileManager("config.yml", dataFolder)
        val configuration = yamlSerializer.toClassOrDefault(
            configFile.configFile,
            ::MainConfiguration
        )
        if (!configFile.configFile.exists()) {
            configFile.configFile.createNewFile()
            configFile.configFile.writeText(yamlSerializer.yaml.encodeToString(configuration))
        }
        return configuration
    }
}