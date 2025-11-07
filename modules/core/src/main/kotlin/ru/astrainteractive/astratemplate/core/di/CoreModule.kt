package ru.astrainteractive.astratemplate.core.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.core.plugin.PluginConfiguration
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultMutableKrate
import ru.astrainteractive.klibs.kstorage.util.asCachedKrate
import ru.astrainteractive.klibs.kstorage.util.asStateFlowKrate
import java.io.File
import ru.astrainteractive.astralibs.async.withTimings
import ru.astrainteractive.astralibs.util.YamlStringFormat
import ru.astrainteractive.astralibs.util.parseOrWriteIntoDefault
import ru.astrainteractive.klibs.mikro.core.coroutines.CoroutineFeature

class CoreModule(dataFolder: File) {
    val yamlStringFormat = YamlStringFormat()

    val ioScope = CoroutineFeature.IO.withTimings()

    val translationKrate = DefaultMutableKrate(
        factory = ::PluginTranslation,
        loader = {
            yamlStringFormat.parseOrWriteIntoDefault(
                file = dataFolder.resolve("translation.yml"),
                default = ::PluginTranslation
            )
        }
    ).asCachedKrate()

    val configKrate = DefaultMutableKrate(
        factory = ::PluginConfiguration,
        loader = {
            yamlStringFormat.parseOrWriteIntoDefault(
                file = dataFolder.resolve("translation.yml"),
                default = ::PluginConfiguration
            )
        }
    ).asStateFlowKrate()

    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda(
            onReload = {
                configKrate.getValue()
                translationKrate.getValue()
            },
            onDisable = {
                ioScope.cancel()
            }
        )
    }
}
