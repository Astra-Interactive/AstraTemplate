package ru.astrainteractive.astratemplate.core.di

import com.charleskorn.kaml.PolymorphismStyle
import com.charleskorn.kaml.Yaml
import java.io.File
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.serialization.StringFormat
import ru.astrainteractive.aspekt.BuildKonfig
import ru.astrainteractive.astralibs.coroutines.withTimings
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astralibs.util.YamlStringFormat
import ru.astrainteractive.astralibs.util.parseOrWriteIntoDefault
import ru.astrainteractive.astratemplate.core.plugin.PluginConfiguration
import ru.astrainteractive.astratemplate.core.plugin.PluginTranslation
import ru.astrainteractive.klibs.kstorage.api.asCachedKrate
import ru.astrainteractive.klibs.kstorage.api.asStateFlowKrate
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultMutableKrate
import ru.astrainteractive.klibs.mikro.core.coroutines.CoroutineFeature
import ru.astrainteractive.klibs.mikro.core.dispatchers.KotlinDispatchers
import ru.astrainteractive.klibs.mikro.core.logging.JUtiltLogger

class CoreModule(
    val dataFolder: File,
    val dispatchers: KotlinDispatchers
) {
    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        val logger = JUtiltLogger("${BuildKonfig.id}-CoroutineExceptionHandler")
        logger.error(throwable) { "Error happened inside global coroutine scope!" }
    }

    val ioScope = CoroutineFeature
        .Default(dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)
        .withTimings()

    val mainScope: CoroutineScope = CoroutineFeature
        .Default(dispatchers.Main + SupervisorJob() + coroutineExceptionHandler)
        .withTimings()

    val unconfinedScope = CoroutineFeature
        .Default(dispatchers.Unconfined + SupervisorJob() + coroutineExceptionHandler)
        .withTimings()

    val yamlFormat: StringFormat = YamlStringFormat(
        configuration = Yaml.default.configuration.copy(
            encodeDefaults = true,
            strictMode = false,
            polymorphismStyle = PolymorphismStyle.Property
        ),
    )

    val translationKrate = DefaultMutableKrate(
        factory = ::PluginTranslation,
        loader = {
            yamlFormat.parseOrWriteIntoDefault(
                file = dataFolder.resolve("translation.yml"),
                default = ::PluginTranslation
            )
        }
    ).asCachedKrate()

    val configKrate = DefaultMutableKrate(
        factory = ::PluginConfiguration,
        loader = {
            yamlFormat.parseOrWriteIntoDefault(
                file = dataFolder.resolve("translation.yml"),
                default = ::PluginConfiguration
            )
        }
    ).asStateFlowKrate()

    val kyoriKrate = DefaultMutableKrate<KyoriComponentSerializer>(
        loader = { null },
        factory = { KyoriComponentSerializer.Legacy }
    ).asCachedKrate()

    val lifecycle: Lifecycle by lazy {
        Lifecycle.Lambda(
            onReload = {
                configKrate.getValue()
                translationKrate.getValue()
            },
            onDisable = {
                ioScope.cancel()
                unconfinedScope.cancel()
                mainScope.cancel()
            }
        )
    }
}
