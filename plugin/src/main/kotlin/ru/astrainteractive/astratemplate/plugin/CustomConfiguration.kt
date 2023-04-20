package ru.astrainteractive.astratemplate.plugin

import ru.astrainteractive.astralibs.configuration.DefaultConfiguration
import ru.astrainteractive.astralibs.configuration.api.Configuration
import ru.astrainteractive.astralibs.configuration.api.MutableConfiguration

/**
 * This class contains configuration which is parsed without kotlinx.serialization
 * @see Configuration
 * @see MutableConfiguration
 * @see DefaultConfiguration
 */
class CustomConfiguration(
    val pluginVersion: Configuration<String>
)
