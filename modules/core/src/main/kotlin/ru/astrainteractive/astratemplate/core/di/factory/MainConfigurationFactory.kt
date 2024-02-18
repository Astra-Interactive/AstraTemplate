package ru.astrainteractive.astratemplate.core.di.factory

import kotlinx.serialization.encodeToString
import ru.astrainteractive.astralibs.filemanager.impl.JVMFileManager
import ru.astrainteractive.astralibs.serialization.SerializerExt.parseOrDefault
import ru.astrainteractive.astralibs.serialization.YamlSerializer
import ru.astrainteractive.astratemplate.core.PluginConfiguration
import ru.astrainteractive.klibs.kdi.Factory
import java.io.File

internal class MainConfigurationFactory(
    private val dataFolder: File,
    private val yamlSerializer: YamlSerializer
) : Factory<PluginConfiguration> {

    override fun create(): PluginConfiguration {
        val configFile = JVMFileManager("config.yml", dataFolder)
        val configuration = yamlSerializer.parseOrDefault(
            configFile.configFile,
            ::PluginConfiguration
        )
        if (!configFile.configFile.exists()) {
            configFile.configFile.createNewFile()
        }
        configFile.configFile.writeText(yamlSerializer.yaml.encodeToString(configuration))
        return configuration
    }
}
