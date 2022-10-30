package ru.astrainteractive.astratemplate.utils

import org.bukkit.configuration.file.FileConfiguration
import ru.astrainteractive.astralibs.file_manager.FileManager
import ru.astrainteractive.astralibs.utils.HEX
import ru.astrainteractive.astralibs.utils.getHEXString

abstract class BaseTranslation {
    protected abstract val translationFile: FileManager
    protected val translationConfiguration: FileConfiguration
        get() = translationFile.fileConfiguration

    /**
     * This function will write non-existing translation into config file
     * So you don't need to create your config file by yourself
     * Just run plugin with this function and translation file will be generated automatically
     */
    fun translationValue(path: String, default: String): String {
        val msg = translationConfiguration.getHEXString(path) ?: default.HEX()
        if (!translationConfiguration.contains(path)) {
            translationConfiguration.set(path, default)
            translationFile.save()
        }
        return msg
    }
}