package ru.astrainteractive.astratemplate.core.di.factory

import kotlinx.serialization.StringFormat
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.klibs.kdi.Factory
import java.io.File

internal class TranslationFactory(
    private val dataFolder: File,
    private val stringFormat: StringFormat
) : Factory<PluginTranslation> {

    override fun create(): PluginTranslation {
        return ConfigKrateFactory(
            dataFolder = dataFolder,
            stringFormat = stringFormat,
            fileNameWithoutExtension = "translations"
        ).create(
            PluginTranslation.serializer(),
            ::PluginTranslation
        )
    }
}
