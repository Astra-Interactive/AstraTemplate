package ru.astrainteractive.astratemplate.core.di.factory

import kotlinx.serialization.KSerializer
import kotlinx.serialization.StringFormat
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.serialization.StringFormatExt.parse
import ru.astrainteractive.astralibs.serialization.StringFormatExt.writeIntoFile
import java.io.File

class ConfigKrateFactory(
    private val dataFolder: File,
    private val stringFormat: StringFormat,
    private val fileNameWithoutExtension: String
) : Logger by JUtiltLogger("AstraRating-ConfigKrateFactory") {
    fun <T> create(kSerializer: KSerializer<T>, factory: () -> T): T {
        val file = dataFolder.resolve("$fileNameWithoutExtension.yml")
        val defaultFile = dataFolder.resolve("$fileNameWithoutExtension.default.yml")
        return stringFormat.parse(kSerializer, file)
            .onFailure {
                defaultFile.createNewFile()
                stringFormat.writeIntoFile(kSerializer, factory.invoke(), defaultFile)
                error { "Could not read $fileNameWithoutExtension.yml! Loaded default. Error -> ${it.message}" }
            }
            .onSuccess {
                stringFormat.writeIntoFile(kSerializer, it, file)
            }
            .getOrElse { factory.invoke() }
    }
}
