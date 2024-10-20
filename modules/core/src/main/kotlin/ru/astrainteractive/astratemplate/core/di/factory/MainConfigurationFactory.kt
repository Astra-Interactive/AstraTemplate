package ru.astrainteractive.astratemplate.core.di.factory

import kotlinx.serialization.StringFormat
import ru.astrainteractive.astratemplate.core.PluginConfiguration
import ru.astrainteractive.klibs.kdi.Factory
import java.io.File

internal class MainConfigurationFactory(
    private val dataFolder: File,
    private val stringFormat: StringFormat
) : Factory<PluginConfiguration> {

    override fun create(): PluginConfiguration {
        return ConfigKrateFactory(
            dataFolder = dataFolder,
            stringFormat = stringFormat,
            fileNameWithoutExtension = "config"
        ).create(PluginConfiguration.serializer(), ::PluginConfiguration)
    }
}
