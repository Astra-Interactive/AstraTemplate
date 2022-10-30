package ru.astrainteractive.astratemplate.utils

import ru.astrainteractive.astralibs.file_manager.FileManager


/**
 * All plugin files such as config.yml and other should only be stored here!
 */
object Files {
    val configFile: FileManager =
        FileManager("config.yml")
}