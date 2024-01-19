@file:Suppress("LongParameterList")

package ru.astrainteractive.astratemplate.core

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.astrainteractive.astralibs.string.StringDesc
import ru.astrainteractive.astralibs.string.StringDescExt.replace

/**
 * All translation stored here
 * Each translation have default value so it's not necesarry to fetch it from resources
 */
@Serializable
class PluginTranslation(
    val database: Database = Database(),
    val menu: Menu = Menu(),
    val custom: Custom = Custom(),
    val general: General = General()
) {
    @Serializable
    class Database(
        @SerialName("success")
        val dbSuccess: StringDesc.Raw = StringDesc.Raw("&#18dbd1Успешно подключено к базе данных"),
        @SerialName("fail")
        val dbFail: StringDesc.Raw = StringDesc.Raw("&#db2c18Нет подключения к базе данных"),
    )

    @Serializable
    class General(
        @SerialName("prefix")
        val prefix: StringDesc.Raw = StringDesc.Raw("&#18dbd1[EmpireItems]"),
        @SerialName("reload")
        val reload: StringDesc.Raw = StringDesc.Raw("&#dbbb18Перезагрузка плагина"),
        @SerialName("reload_complete")
        val reloadComplete: StringDesc.Raw = StringDesc.Raw("&#42f596Перезагрузка успешно завершена"),
        @SerialName("no_permission")
        val noPermission: StringDesc.Raw = StringDesc.Raw("&#db2c18У вас нет прав!"),
        @SerialName("not_player")
        val notPlayer: StringDesc.Raw = StringDesc.Raw("&#db2c18Вы не игрок"),
        @SerialName("getByByCheck")
        val getByByCheck: StringDesc.Raw = StringDesc.Raw("&#db2c18getByByCheck")
    )

    @Serializable
    class Menu(
        @SerialName("title")
        val menuTitle: StringDesc.Raw = StringDesc.Raw("&#18dbd1Меню"),
        @SerialName("add_player")
        val menuAddPlayer: StringDesc.Raw = StringDesc.Raw("&#18dbd1Добавить игрока"),
        @SerialName("first_page")
        val menuFirstPage: StringDesc.Raw = StringDesc.Raw("&#dbbb18Вы на первой странице"),
        @SerialName("last_page")
        val menuLastPage: StringDesc.Raw = StringDesc.Raw("&#dbbb18Вы на последней странице"),
        @SerialName("prev_page")
        val menuPrevPage: StringDesc.Raw = StringDesc.Raw("&#18dbd1Пред. страницы"),
        @SerialName("next_page")
        val menuNextPage: StringDesc.Raw = StringDesc.Raw("&#18dbd1След. страница"),
        @SerialName("back")
        val menuBack: StringDesc.Raw = StringDesc.Raw("&#18dbd1Назад"),
        @SerialName("close")
        val menuClose: StringDesc.Raw = StringDesc.Raw("&#18dbd1Закрыть)")
    )

    @Serializable
    class Custom(
        @SerialName("block_placed")
        val blockPlaced: StringDesc.Raw = StringDesc.Raw("&#18dbd1Блок поставлен!"),
        @SerialName("no_player_name")
        val noPlayerName: StringDesc.Raw = StringDesc.Raw("&#db2c18Вы не ввели имя игрока!"),
        @SerialName("damaged")
        private val damaged: StringDesc.Raw = StringDesc.Raw("&#db2c18Вас продамажил игрок %player%!"),
        @SerialName("damage_hint")
        val damageHint: StringDesc.Raw = StringDesc.Raw("<amount>")
    ) {
        fun damaged(player: String) = damaged.replace("%player%", player)
    }
}
