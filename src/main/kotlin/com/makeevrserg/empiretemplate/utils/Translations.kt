package com.makeevrserg.empiretemplate.utils

import FileManager
import com.makeevrserg.empiretemplate.EmpireTemplate.Companion.plugin
import org.bukkit.configuration.file.FileConfiguration
import java.io.File


class Translations() {


    private val text = plugin.empireFiles.translationFile.getConfig()

    private fun FileConfiguration.getHEXString(path: String, def: String): String {
        return EmpireUtils.HEXPattern(getString(path, def)!!)
    }

    private fun FileConfiguration.getHEXString(path: String): String? {
        return EmpireUtils.HEXPattern(getString(path))
    }

    private fun String.HEX(): String {
        return EmpireUtils.HEXPattern(this)
    }

    public val PLUGIN_PREFIX: String = text?.getHEXString("PLUGIN_PREFIX") ?: "#18dbd1[EmpireItems]".HEX()
    public val PREV_PAGE: String = text?.getHEXString("PREV_PAGE") ?: "#18dbd1[EmpireItems]".HEX()
    public val NEXT_PAGE: String = text?.getHEXString("NEXT_PAGE") ?: "#18dbd1[EmpireItems]".HEX()
    public val CLOSE: String = text?.getHEXString("CLOSE") ?: "#18dbd1[EmpireItems]".HEX()
    public val DB_SUCCESS: String = text?.getHEXString("DB_SUCCESS") ?: "#18dbd1Успешно подключено к базе данных".HEX()
    public val DB_FAIL: String = text?.getHEXString("DB_FAIL") ?: "#db2c18Нет подключения к базе данных".HEX()
    public val RELOAD: String = text?.getHEXString("RELOAD") ?: "#dbbb18Перезагрузка плагина".HEX()
    public val RELOAD_COMPLETE: String =
        text?.getHEXString("RELOAD_COMPLETE") ?: "#42f596Перезагрузка успешно завершена".HEX()
    public val SAVE_ERROR: String =
        text?.getHEXString("SAVE_ERROR") ?: "#db2c18Не удалось сохранить файл".HEX()
    public val NONSTANDART_FILE: String = text?.getHEXString("NONSTANDART_FILE") ?: "#db2c18Нестнадартный файл".HEX()

    init {
        translations = this
    }

    companion object {
        lateinit var translations: Translations
            private set
    }

    public fun onDisable() {

    }
}