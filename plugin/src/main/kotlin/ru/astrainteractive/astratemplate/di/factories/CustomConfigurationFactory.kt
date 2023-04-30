package ru.astrainteractive.astratemplate.di.factories

import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.configuration.AnyConfiguration
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.di.impl.FilesModuleImpl
import ru.astrainteractive.astratemplate.plugin.CustomConfiguration

internal object CustomConfigurationFactory : Factory<CustomConfiguration> {
    override fun build(): CustomConfiguration {
        val configFile by FilesModuleImpl.configFile
        return CustomConfiguration(
            pluginVersion = AnyConfiguration(
                fileConfiguration = configFile.fileConfiguration,
                path = "custom.version",
                default = "0.88.0"
            )
        )
    }
}
