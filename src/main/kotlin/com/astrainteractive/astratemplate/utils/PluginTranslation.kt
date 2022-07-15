package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.FileManager
import com.astrainteractive.astralibs.utils.HEX
import com.astrainteractive.astralibs.utils.getHEXString

val Translation: PluginTranslation
    get() = PluginTranslation.instance

/**
 * All translation stored here
 */
class PluginTranslation {
    /**
     * For better access better to create [instance]
     */
    companion object {
        internal lateinit var instance: PluginTranslation
    }

    init {
        instance = this
    }

    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    private val _translationFile: FileManager = FileManager("translations.yml")
    private val translation = _translationFile.getConfig()

    private fun getHEXString(path: String) = translation.getHEXString(path)

    /**
     * This function will write non-existing translation into config file
     * So you don't need to create your config file by yourself
     * Just run plugin with this function and translation file will be generated automatically
     */
    private fun getHEXString(path: String, default: String): String {
        val msg = translation.getHEXString(path) ?: default.HEX()
        if (!translation.contains(path)) {
            translation.set(path, default)
            _translationFile.saveConfig()
        }
        return msg
    }

    //Database
    val dbSuccess: String =
        getHEXString("database.success", "#18dbd1Успешно подключено к базе данных")
    val dbFail: String = getHEXString("database.fail", "#db2c18Нет подключения к базе данных")

    //General
    val prefix: String = getHEXString("general.prefix", "#18dbd1[EmpireItems]")
    val reload: String = getHEXString("general.reload", "#dbbb18Перезагрузка плагина")
    val reloadComplete: String =
        getHEXString("general.reload_complete", "#42f596Перезагрузка успешно завершена")
    val noPermission: String =
        getHEXString("general.no_permission", "#db2c18У вас нет прав!")

    //Menu
    val menuTitle: String = getHEXString("menu.title", "#18dbd1Меню")
    val menuAddPlayer: String = getHEXString("menu.add_player", "#18dbd1Добавить игрока")
    val menuFirstPage: String = getHEXString("menu.first_page", "#dbbb18Вы на первой странице")
    val menuLastPage: String = getHEXString("menu.last_page", "#dbbb18Вы на последней странице")
    val menuPrevPage: String = getHEXString("menu.prev_page", "#18dbd1Пред. страницы")
    val menuNextPage: String = getHEXString("menu.next_page", "#18dbd1След. страница")
    val menuBack: String = getHEXString("menu.back", "#18dbd1Назад")
    val menuClose: String = getHEXString("menu.close", "#18dbd1Закрыть")

    //Custom
    val blockPlaced: String = getHEXString("custom.block_placed", "#18dbd1Блок поставлен!")
    val noPlayerName: String = getHEXString("custom.no_player_name", "#db2c18Вы не ввели имя игрока!")
    val damaged: String = getHEXString("custom.damaged", "#db2c18Вас продамажил игрок %player%!")
    val damageHint: String = getHEXString("custom.damage_hint", "<amount>")

}