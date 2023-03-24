package ru.astrainteractive.astratemplate.plugin

import ru.astrainteractive.astralibs.configuration.api.Configuration
import ru.astrainteractive.astralibs.configuration.api.MutableConfiguration
import ru.astrainteractive.astralibs.configuration.DefaultConfiguration

/**
 * This class contains configuration which is parsed without kotlinx.serialization
 * @see Configuration
 * @see MutableConfiguration
 * @see DefaultConfiguration
 */
class CustomConfiguration(
    val pluginVersion: Configuration<String>
)
