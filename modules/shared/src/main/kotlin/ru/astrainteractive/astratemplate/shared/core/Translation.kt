@file:Suppress("LongParameterList")

package ru.astrainteractive.astratemplate.shared.core

import kotlinx.serialization.SerialName
import ru.astrainteractive.astralibs.string.StringDesc

/**
 * All translation stored here
 * Each translation have default value so it's not necesarry to fetch it from resources
 */
class Translation(
    val database: Database = Database(),
    val menu: Menu = Menu(),
    val custom: Custom = Custom(),
    val general: General = General()
) {

    class Database(
        @SerialName("database.success")
        val dbSuccess: StringDesc.Raw = StringDesc.Raw("#18dbd1Успешно подключено к базе данных"),
        @SerialName("database.fail")
        val dbFail: StringDesc.Raw = StringDesc.Raw("#db2c18Нет подключения к базе данных"),
    )

    class General(
        @SerialName("general.prefix")
        val prefix: StringDesc.Raw = StringDesc.Raw("#18dbd1[EmpireItems]"),
        @SerialName("general.reload")
        val reload: StringDesc.Raw = StringDesc.Raw("#dbbb18Перезагрузка плагина"),
        @SerialName("general.reload_complete")
        val reloadComplete: StringDesc.Raw = StringDesc.Raw("#42f596Перезагрузка успешно завершена"),
        @SerialName("general.no_permission")
        val noPermission: StringDesc.Raw = StringDesc.Raw("#db2c18У вас нет прав!"),
        @SerialName("getByByCheck")
        val getByByCheck: StringDesc.Raw = StringDesc.Raw("#db2c18getByByCheck")
    )

    class Menu(
        @SerialName("menu.title")
        val menuTitle: StringDesc.Raw = StringDesc.Raw("#18dbd1Меню"),
        @SerialName("menu.add_player")
        val menuAddPlayer: StringDesc.Raw = StringDesc.Raw("#18dbd1Добавить игрока"),
        @SerialName("menu.first_page")
        val menuFirstPage: StringDesc.Raw = StringDesc.Raw("#dbbb18Вы на первой странице"),
        @SerialName("menu.last_page")
        val menuLastPage: StringDesc.Raw = StringDesc.Raw("#dbbb18Вы на последней странице"),
        @SerialName("menu.prev_page")
        val menuPrevPage: StringDesc.Raw = StringDesc.Raw("#18dbd1Пред. страницы"),
        @SerialName("menu.next_page")
        val menuNextPage: StringDesc.Raw = StringDesc.Raw("#18dbd1След. страница"),
        @SerialName("menu.back")
        val menuBack: StringDesc.Raw = StringDesc.Raw("#18dbd1Назад"),
        @SerialName("menu.close")
        val menuClose: StringDesc.Raw = StringDesc.Raw("#18dbd1Закрыть)")
    )

    class Custom(
        @SerialName("custom.block_placed")
        val blockPlaced: StringDesc.Raw = StringDesc.Raw("#18dbd1Блок поставлен!"),
        @SerialName("custom.no_player_name")
        val noPlayerName: StringDesc.Raw = StringDesc.Raw("#db2c18Вы не ввели имя игрока!"),
        @SerialName("custom.damaged")
        val damaged: StringDesc.Raw = StringDesc.Raw("#db2c18Вас продамажил игрок %player%!"),
        @SerialName("custom.damage_hint")
        val damageHint: StringDesc.Raw = StringDesc.Raw("<amount>")
    )
}
