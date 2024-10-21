package ru.astrainteractive.astratemplate.core.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.serialization.StringFormat
import ru.astrainteractive.astralibs.async.CoroutineFeature
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.serialization.YamlStringFormat
import ru.astrainteractive.astratemplate.core.PluginConfiguration
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.astratemplate.core.di.factory.ConfigKrateFactory
import ru.astrainteractive.klibs.kstorage.api.flow.StateFlowKrate
import java.io.File

interface CoreModule {

    val lifecycle: Lifecycle
    val stringFormat: StringFormat
    val pluginScope: CoroutineScope
    val translation: StateFlowKrate<PluginTranslation>
    val configurationModule: StateFlowKrate<PluginConfiguration>

    class Default(
        dataFolder: File
    ) : CoreModule {
        override val stringFormat = YamlStringFormat()

        override val pluginScope = CoroutineFeature.Default(Dispatchers.IO)

        override val translation = ConfigKrateFactory.create(
            fileNameWithoutExtension = "translations",
            stringFormat = stringFormat,
            dataFolder = dataFolder,
            factory = ::PluginTranslation
        )

        override val configurationModule = ConfigKrateFactory.create(
            fileNameWithoutExtension = "config",
            stringFormat = stringFormat,
            dataFolder = dataFolder,
            factory = ::PluginConfiguration
        )
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onReload = {
                    configurationModule.loadAndGet()
                    translation.loadAndGet()
                },
                onDisable = {
                    pluginScope.cancel()
                }
            )
        }
    }
}
