package com.makeevrserg.empireprojekt.util.files

import FileManager
import java.io.File

public class Files() {

    val configFile: FileManager =
        FileManager("config.yml")
    val translationFile: FileManager =
        FileManager("translations.yml")

    val menuConfigFile: FileManager =
        FileManager("menu/config.yml")

}