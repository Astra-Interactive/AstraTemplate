package ru.astrainteractive.astratemplate.core.di.factory

import kotlinx.serialization.StringFormat
import kotlinx.serialization.encodeToString
import ru.astrainteractive.astralibs.filemanager.impl.JVMFileManager
import ru.astrainteractive.astralibs.serialization.StringFormatExt.parseOrDefault
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.klibs.kdi.Factory
import java.io.File

internal class TranslationFactory(
    private val dataFolder: File,
    private val stringFormat: StringFormat
) : Factory<PluginTranslation> {

    override fun create(): PluginTranslation {
        val configFile = JVMFileManager("translations.yml", dataFolder)
        val translation = stringFormat.parseOrDefault(
            configFile.configFile,
            ::PluginTranslation
        )
        if (!configFile.configFile.exists()) {
            configFile.configFile.createNewFile()
        }
        configFile.configFile.writeText(stringFormat.encodeToString(translation))
        return translation
    }
}
