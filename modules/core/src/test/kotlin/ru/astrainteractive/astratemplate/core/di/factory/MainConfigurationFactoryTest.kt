package ru.astrainteractive.astratemplate.core.di.factory

import ru.astrainteractive.astralibs.serialization.YamlStringFormat
import ru.astrainteractive.astratemplate.core.PluginConfiguration
import java.io.File
import kotlin.test.Test

class MainConfigurationFactoryTest {
    @Test
    fun GIVEN_default_factory_WHEN_call_THEN_no_throw() {
        ConfigKrateFactory.create(
            dataFolder = File(System.getProperty("java.io.tmpdir")),
            stringFormat = YamlStringFormat(),
            factory = ::PluginConfiguration,
            fileNameWithoutExtension = "config"
        )
    }
}
