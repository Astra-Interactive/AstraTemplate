package ru.astrainteractive.astratemplate.plugin

import ru.astrainteractive.astralibs.file_manager.FileManager
import ru.astrainteractive.astralibs.utils.BaseTranslation

/**
 * All translation stored here
 */
class Translation : BaseTranslation() {
    /**
     * This is a default translation file. Don't forget to create translation.yml in resources of the plugin
     */
    protected override val translationFile: FileManager = FileManager("translations.yml")

    val getByByCheck = translationValue("getByByCheck", "#db2c18getByByCheck")

    // Database
    val dbSuccess = translationValue("database.success", "#18dbd1Успешно подключено к базе данных")
    val dbFail = translationValue("database.fail", "#db2c18Нет подключения к базе данных")

    // General
    val prefix = translationValue("general.prefix", "#18dbd1[EmpireItems]")
    val reload = translationValue("general.reload", "#dbbb18Перезагрузка плагина")
    val reloadComplete = translationValue("general.reload_complete", "#42f596Перезагрузка успешно завершена")
    val noPermission = translationValue("general.no_permission", "#db2c18У вас нет прав!")

    // Menu
    val menuTitle = translationValue("menu.title", "#18dbd1Меню")
    val menuAddPlayer = translationValue("menu.add_player", "#18dbd1Добавить игрока")
    val menuFirstPage = translationValue("menu.first_page", "#dbbb18Вы на первой странице")
    val menuLastPage = translationValue("menu.last_page", "#dbbb18Вы на последней странице")
    val menuPrevPage = translationValue("menu.prev_page", "#18dbd1Пред. страницы")
    val menuNextPage = translationValue("menu.next_page", "#18dbd1След. страница")
    val menuBack = translationValue("menu.back", "#18dbd1Назад")
    val menuClose = translationValue("menu.close", "#18dbd1Закрыть")

    // Custom
    val blockPlaced = translationValue("custom.block_placed", "#18dbd1Блок поставлен!")
    val noPlayerName = translationValue("custom.no_player_name", "#db2c18Вы не ввели имя игрока!")
    val damaged = translationValue("custom.damaged", "#db2c18Вас продамажил игрок %player%!")
    val damageHint = translationValue("custom.damage_hint", "<amount>")
}
