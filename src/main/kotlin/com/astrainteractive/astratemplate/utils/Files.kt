package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.FileManager


val Files: _Files
    get() = _Files.instance

/**
 * All plugin files such as config.yml and other should only be stored here!
 */
class _Files {
    companion object {
        lateinit var instance: _Files
    }
    init {
        instance = this
    }

    val configFile: FileManager =
        FileManager("config.yml")
}