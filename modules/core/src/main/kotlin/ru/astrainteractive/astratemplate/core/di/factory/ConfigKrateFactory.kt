package ru.astrainteractive.astratemplate.core.di.factory

import kotlinx.serialization.StringFormat
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.serialization.StringFormatExt.parse
import ru.astrainteractive.astralibs.serialization.StringFormatExt.writeIntoFile
import ru.astrainteractive.klibs.kstorage.api.impl.DefaultStateFlowMutableKrate
import ru.astrainteractive.klibs.kstorage.api.value.ValueFactory
import java.io.File

object ConfigKrateFactory : Logger by JUtiltLogger("AstraMarket-ConfigKrateFactory") {
    inline fun <reified T> create(
        fileNameWithoutExtension: String,
        stringFormat: StringFormat,
        dataFolder: File,
        factory: ValueFactory<T>
    ): DefaultStateFlowMutableKrate<T> {
        return DefaultStateFlowMutableKrate(
            factory = factory,
            loader = {
                val file = dataFolder.resolve("$fileNameWithoutExtension.yml")
                stringFormat.parse<T>(file)
                    .onFailure {
                        stringFormat.writeIntoFile(
                            value = factory.create(),
                            file = when {
                                !file.exists() || file.length() == 0L -> file
                                else -> dataFolder.resolve("$fileNameWithoutExtension.default.yml")
                            }
                        )
                        error { "Could not read $fileNameWithoutExtension.yml! Loaded default. Error -> ${it.message}" }
                    }
                    .onSuccess { stringFormat.writeIntoFile(it, file) }
                    .getOrElse { factory.create() }
            }
        )
    }
}
