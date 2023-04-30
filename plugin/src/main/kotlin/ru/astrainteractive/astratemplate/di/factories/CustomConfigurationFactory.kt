package ru.astrainteractive.astratemplate.di.factories

import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.configuration.AnyConfiguration
import ru.astrainteractive.astratemplate.di.FilesModule
import ru.astrainteractive.astratemplate.plugin.CustomConfiguration

internal object CustomConfigurationFactory : Factory<CustomConfiguration> {
    override fun build(): CustomConfiguration {
        val fileConfiguration = FilesModule.configFile.value.fileConfiguration
        return CustomConfiguration(
            pluginVersion = AnyConfiguration(
                fileConfiguration = fileConfiguration,
                path = "custom.version",
                default = "0.88.0"
            )
        )
    }
}
