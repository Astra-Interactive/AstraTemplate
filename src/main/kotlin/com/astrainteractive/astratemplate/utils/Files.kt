package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.FileManager


/**
 * All plugin files such as config.yml and other should only be stored here!
 */
class Files {
    val configFile: FileManager =
        FileManager("config.yml")
}