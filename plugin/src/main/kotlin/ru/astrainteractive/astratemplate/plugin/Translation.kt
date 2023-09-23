package ru.astrainteractive.astratemplate.plugin

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.filemanager.DefaultSpigotFileManager
import ru.astrainteractive.astralibs.filemanager.SpigotFileManager
import ru.astrainteractive.astralibs.util.BaseTranslation

/**
 * All translation stored here
 * Each translation have default value so it's not necesarry to fetch it from resources
 */
class Translation(plugin: Plugin) : BaseTranslation() {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    override val translationFile: SpigotFileManager = DefaultSpigotFileManager(plugin, "translations.yml")

    val database = Database()
    val menu = Menu()
    val custom = Custom()
    val general = General()

    inner class Database {
        val dbSuccess = translationValue("database.success", "#18dbd1Успешно подключено к базе данных")
        val dbFail = translationValue("database.fail", "#db2c18Нет подключения к базе данных")
    }

    inner class General {
        val prefix = translationValue("general.prefix", "#18dbd1[EmpireItems]")
        val reload = translationValue("general.reload", "#dbbb18Перезагрузка плагина")
        val reloadComplete = translationValue("general.reload_complete", "#42f596Перезагрузка успешно завершена")
        val noPermission = translationValue("general.no_permission", "#db2c18У вас нет прав!")
        val getByByCheck = translationValue("getByByCheck", "#db2c18getByByCheck")
    }

    inner class Menu {
        val menuTitle = translationValue("menu.title", "#18dbd1Меню")
        val menuAddPlayer = translationValue("menu.add_player", "#18dbd1Добавить игрока")
        val menuFirstPage = translationValue("menu.first_page", "#dbbb18Вы на первой странице")
        val menuLastPage = translationValue("menu.last_page", "#dbbb18Вы на последней странице")
        val menuPrevPage = translationValue("menu.prev_page", "#18dbd1Пред. страницы")
        val menuNextPage = translationValue("menu.next_page", "#18dbd1След. страница")
        val menuBack = translationValue("menu.back", "#18dbd1Назад")
        val menuClose = translationValue("menu.close", "#18dbd1Закрыть")
    }

    inner class Custom {
        val blockPlaced = translationValue("custom.block_placed", "#18dbd1Блок поставлен!")
        val noPlayerName = translationValue("custom.no_player_name", "#db2c18Вы не ввели имя игрока!")
        val damaged = translationValue("custom.damaged", "#db2c18Вас продамажил игрок %player%!")
        val damageHint = translationValue("custom.damage_hint", "<amount>")
    }
}
