package ru.astrainteractive.astratemplate.core.di

import kotlinx.coroutines.cancel
import ru.astrainteractive.astralibs.async.withTimings
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.util.YamlStringFormat
import ru.astrainteractive.astralibs.util.parseOrWriteIntoDefault
import ru.astrainteractive.astratemplate.core.plugin.PluginConfiguration
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultMutableKrate
import ru.astrainteractive.klibs.kstorage.util.asCachedKrate
import ru.astrainteractive.klibs.kstorage.util.asStateFlowKrate
import ru.astrainteractive.klibs.mikro.core.coroutines.CoroutineFeature
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import java.io.File

class CoreModule(
    val dataFolder: File,
    val dispatchers: KotlinDispatchers
) {
    val yamlStringFormat = YamlStringFormat()

    val ioScope = CoroutineFeature.IO.withTimings()
    val mainScope = CoroutineFeature
        .Default(dispatchers.Main)
        .withTimings()

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
