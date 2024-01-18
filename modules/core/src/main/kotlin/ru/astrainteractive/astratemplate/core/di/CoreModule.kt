package ru.astrainteractive.astratemplate.core.di

import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.logging.JUtilFileLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.serialization.YamlSerializer
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
    val yamlSerializer: Dependency<YamlSerializer>
    val logger: Dependency<Logger>
    val pluginScope: Dependency<AsyncComponent>
    val translation: Dependency<PluginTranslation>
    val configurationModule: Dependency<PluginConfiguration>

    class Default(
        dataFolder: File
    ) : CoreModule {
        override val yamlSerializer: Single<YamlSerializer> = Single {
            YamlSerializer()
        }
        override val logger: Dependency<Logger> = Single {
            JUtilFileLogger(
                tag = "AstraTemplate",
                folder = dataFolder
            )
        }

        override val pluginScope = Single {
            AsyncComponent.Default()
        }

        override val translation: Reloadable<PluginTranslation> = Reloadable {
            TranslationFactory(
                dataFolder = dataFolder,
                yamlSerializer = yamlSerializer.value
            ).create()
        }

        override val configurationModule: Reloadable<PluginConfiguration> = Reloadable {
            MainConfigurationFactory(
                dataFolder = dataFolder,
                yamlSerializer = yamlSerializer.value
            ).create()
        }
        override val lifecycle: Lifecycle by lazy {
            Lifecycle.Lambda(
                onReload = {
                    configurationModule.reload()
                    translation.reload()
                },
                onDisable = {
                    pluginScope.value.close()
                }
            )
        }
    }
}
