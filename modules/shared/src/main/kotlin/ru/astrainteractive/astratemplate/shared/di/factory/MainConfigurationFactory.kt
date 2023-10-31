package ru.astrainteractive.astratemplate.shared.di.factory

import kotlinx.serialization.encodeToString
import ru.astrainteractive.astralibs.filemanager.impl.JVMFileManager
import ru.astrainteractive.astralibs.serialization.YamlSerializer
import ru.astrainteractive.astratemplate.shared.core.MainConfiguration
import ru.astrainteractive.klibs.kdi.Factory
import java.io.File
import ru.astrainteractive.astralibs.filemanager.impl.JVMResourceFileManager

internal class MainConfigurationFactory(
    private val dataFolder: File,
    private val yamlSerializer: YamlSerializer
) : Factory<MainConfiguration> {
    override fun create(): MainConfiguration {
        val configFile = JVMResourceFileManager("config.yml", dataFolder, this::class.java)
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
