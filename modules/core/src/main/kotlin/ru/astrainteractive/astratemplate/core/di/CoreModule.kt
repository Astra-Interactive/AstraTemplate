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
import ru.astrainteractive.astratemplate.core.di.factory.MainConfigurationFactory
import ru.astrainteractive.astratemplate.core.di.factory.TranslationFactory
import ru.astrainteractive.klibs.kdi.Dependency
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import java.io.File

interface CoreModule {

    val lifecycle: Lifecycle
    val stringFormat: Dependency<StringFormat>
    val pluginScope: Dependency<CoroutineScope>
    val translation: Dependency<PluginTranslation>
    val configurationModule: Dependency<PluginConfiguration>

    class Default(
        dataFolder: File
    ) : CoreModule {
        override val stringFormat: Single<StringFormat> = Single {
            YamlStringFormat()
        }

        override val pluginScope = Single {
            CoroutineFeature.Default(Dispatchers.IO)
        }

        override val translation: Reloadable<PluginTranslation> = Reloadable {
            TranslationFactory(
                dataFolder = dataFolder,
                stringFormat = stringFormat.value
            ).create()
        }

        override val configurationModule: Reloadable<PluginConfiguration> = Reloadable {
            MainConfigurationFactory(
                dataFolder = dataFolder,
                stringFormat = stringFormat.value
            ).create()
        }
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onReload = {
                    configurationModule.reload()
                    translation.reload()
                },
                onDisable = {
                    pluginScope.value.cancel()
                }
            )
        }
    }
}
