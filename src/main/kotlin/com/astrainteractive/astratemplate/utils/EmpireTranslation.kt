package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.FileManager
import com.astrainteractive.astralibs.HEX
import com.astrainteractive.astralibs.getHEXString

/**
 * All translation stored here by default
 * It's okay to create another translations classes and files, but remember to create Translation class before others
 */
class EmpireTranslation() {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     * translation contains non-null assert because translation.yml exist in every possible situation except whe you forget to add it to resources
     */
    val _translationFile: FileManager = FileManager("translations.yml")
    private val translation = _translationFile.getConfig()!!

    public val PLUGIN_PREFIX: String = translation.getHEXString("PLUGIN_PREFIX") ?: "#18dbd1[EmpireItems]".HEX()
    public val DB_SUCCESS: String =
        translation.getHEXString("DB_SUCCESS") ?: "#18dbd1Успешно подключено к базе данных".HEX()
    //Database
    public val DB_FAIL: String = translation.getHEXString("DB_FAIL") ?: "#db2c18Нет подключения к базе данных".HEX()
    public val RELOAD: String = translation.getHEXString("RELOAD") ?: "#dbbb18Перезагрузка плагина".HEX()

    //Menu
    public val FIRST_PAGE: String = translation.getHEXString("FIRST_PAGE") ?: "#dbbb18Вы на первой странице".HEX()
    public val LAST_PAGE: String = translation.getHEXString("LAST_PAGE") ?: "#dbbb18Вы на последней странице".HEX()

    public val PREV_PAGE: String = translation.getHEXString("PREV_PAGE") ?: "#18dbd1Пред. страницы".HEX()
    public val NEXT_PAGE: String = translation.getHEXString("NEXT_PAGE") ?: "#18dbd1След. страница".HEX()
    public val BACK_PAGE: String = translation.getHEXString("PREV_PAGE") ?: "#18dbd1Назад".HEX()
    public val CLOSE: String = translation.getHEXString("CLOSE") ?: "#18dbd1[EmpireItems]".HEX()

    public val RELOAD_COMPLETE: String =
        translation.getHEXString("RELOAD_COMPLETE") ?: "#42f596Перезагрузка успешно завершена".HEX()
    //Files
    public val SAVE_ERROR: String =
        translation.getHEXString("SAVE_ERROR") ?: "#db2c18Не удалось сохранить файл".HEX()
    public val NONSTANDART_FILE: String =
        translation.getHEXString("NONSTANDART_FILE") ?: "#db2c18Нестнадартный файл".HEX()
    val FILE_WRONG_PARSE: String =
        translation.getHEXString("FILE_WRONG_PARSE", "#f55442 Ошибка при парсинге файла: ")


}