package ru.astrainteractive.astratemplate.modules.factories

import ru.astrainteractive.astralibs.configuration.AnyConfiguration
import ru.astrainteractive.astralibs.di.Factory
import ru.astrainteractive.astratemplate.plugin.CustomConfiguration
import ru.astrainteractive.astratemplate.plugin.Files

object CustomConfigurationFactory : Factory<CustomConfiguration>() {
    override fun initializer(): CustomConfiguration {
        val fileConfiguration = Files.configFile.fileConfiguration
        return CustomConfiguration(
            pluginVersion = AnyConfiguration(
                fileConfiguration = fileConfiguration,
                path = "custom.version",
                default = "0.88.0"
            )
        )
    }
}
