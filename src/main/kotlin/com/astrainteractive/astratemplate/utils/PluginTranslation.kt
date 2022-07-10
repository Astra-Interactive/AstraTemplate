package com.astrainteractive.astratemplate.utils

import com.astrainteractive.astralibs.FileManager
import com.astrainteractive.astralibs.HEX
import com.astrainteractive.astralibs.getHEXString

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
    private fun getHEXString(path: String, default: String) = translation.getHEXString(path) ?: default.HEX()


    //Database
    val dbSuccess: String =
        getHEXString("database.success") ?: "#18dbd1Успешно подключено к базе данных".HEX()
    val dbFail: String = getHEXString("database.fail") ?: "#db2c18Нет подключения к базе данных".HEX()

    //General
    val prefix: String = getHEXString("general.prefix") ?: "#18dbd1[EmpireItems]".HEX()
    val reload: String = getHEXString("general.reload") ?: "#dbbb18Перезагрузка плагина".HEX()
    val reloadComplete: String =
        getHEXString("general.reload_complete") ?: "#42f596Перезагрузка успешно завершена".HEX()
    val noPermission: String =
        getHEXString("general.no_permission") ?: "#db2c18У вас нет прав!".HEX()

    //Menu
    val menuTitle: String = getHEXString("menu.title") ?: "#18dbd1Меню".HEX()
    val menuAddPlayer: String = getHEXString("menu.add_player") ?: "#18dbd1Добавить игрока".HEX()
    val menuFirstPage: String = getHEXString("menu.first_page") ?: "#dbbb18Вы на первой странице".HEX()
    val menuLastPage: String = getHEXString("menu.last_page") ?: "#dbbb18Вы на последней странице".HEX()
    val menuPrevPage: String = getHEXString("menu.prev_page") ?: "#18dbd1Пред. страницы".HEX()
    val menuNextPage: String = getHEXString("menu.next_page") ?: "#18dbd1След. страница".HEX()
    val menuBack: String = getHEXString("menu.back") ?: "#18dbd1Назад".HEX()
    val menuClose: String = getHEXString("menu.close") ?: "#18dbd1Закрыть".HEX()

    //Custom
    val blockPlaced: String = getHEXString("custom.block_placed") ?: "#18dbd1Блок поставлен!".HEX()
    val noPlayerName: String = getHEXString("custom.no_player_name") ?: "#db2c18Вы не ввели имя игрока!".HEX()
    val damaged: String = getHEXString("custom.damaged") ?: "#db2c18Вас продамажил игрок %player%!".HEX()
    val damageHint: String = getHEXString("custom.damage_hint") ?: "<amount>".HEX()


}