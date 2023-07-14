package ru.astrainteractive.astratemplate.di.factory

import ru.astrainteractive.astralibs.configuration.AnyConfiguration
import ru.astrainteractive.astratemplate.di.FilesModule
import ru.astrainteractive.astratemplate.plugin.CustomConfiguration
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.getValue

internal class CustomConfigurationFactory(
    filesModule: FilesModule
) : Factory<CustomConfiguration> {
    private val configFile by filesModule.configFile

    override fun create(): CustomConfiguration {
        return CustomConfiguration(
            pluginVersion = AnyConfiguration(
                fileConfiguration = configFile.fileConfiguration,
                path = "custom.version",
                default = "0.88.0"
            )
        )
    }
}
