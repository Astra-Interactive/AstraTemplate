@file:Suppress("LongParameterList")

package ru.astrainteractive.astratemplate.core.plugin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.astrainteractive.astralibs.string.StringDesc
import ru.astrainteractive.astralibs.string.plus
import ru.astrainteractive.astralibs.string.replace
import ru.astrainteractive.astralibs.string.toRaw

/**
 * All translation stored here
 * Each translation have default value so it's not necessary to fetch it from resources
 */
@Serializable
class PluginTranslation(
    @SerialName("database")
    val database: Database = Database(),
    @SerialName("menu")
    val menu: Menu = Menu(),
    @SerialName("custom")
    val custom: Custom = Custom(),
    @SerialName("general")
    val general: General = General(),
    @SerialName("fault")
    val fault: Fault = Fault()
) {
    @Serializable
    data class Fault(
        @SerialName("no_permission")
        val noPermission: StringDesc.Raw = PREFIX
            .plus("&#db2c18У вас нет прав!")
            .toRaw(),
        @SerialName("not_player")
        val notPlayer: StringDesc.Raw = PREFIX
            .plus("&#db2c18Вы не игрок")
            .toRaw(),
        @SerialName("player_not_exists")
        val playerNotExists: StringDesc.Raw = PREFIX
            .plus("&#db2c18Игрока нет!")
            .toRaw(),
        @SerialName("item_not_found")
        val itemNotFound: StringDesc.Raw = PREFIX
            .plus("&#db2c18Предмет не найден")
            .toRaw(),
    )

    @Serializable
    class Database(
        @SerialName("success")
        val dbSuccess: StringDesc.Raw = PREFIX
            .plus("&#18dbd1Успешно подключено к базе данных")
            .toRaw(),
        @SerialName("fail")
        val dbFail: StringDesc.Raw = PREFIX
            .plus("&#db2c18Нет подключения к базе данных")
            .toRaw(),
    )

    @Serializable
    class General(
        @SerialName("reload")
        val reload: StringDesc.Raw = PREFIX
            .plus("&#dbbb18Перезагрузка плагина")
            .toRaw(),
        @SerialName("reload_complete")
        val reloadComplete: StringDesc.Raw = PREFIX
            .plus("&#42f596Перезагрузка успешно завершена")
            .toRaw(),
        @SerialName("getByByCheck")
        val getByByCheck: StringDesc.Raw = PREFIX
            .plus(StringDesc.Raw("&#db2c18getByByCheck"))
            .toRaw()
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
        val blockPlaced: StringDesc.Raw = PREFIX
            .plus("&#18dbd1Блок поставлен!")
            .toRaw(),
        @SerialName("no_player_name")
        val noPlayerName: StringDesc.Raw = PREFIX
            .plus("&#db2c18Вы не ввели имя игрока!")
            .toRaw(),
        @SerialName("damaged")
        private val damaged: StringDesc.Raw = PREFIX
            .plus("&#db2c18Вас продамажил игрок %player%!")
            .toRaw(),
        @SerialName("damage_hint")
        val damageHint: StringDesc.Raw = StringDesc.Raw("<amount>"),
        @SerialName("add_item_success")
        private val addItemSuccess: StringDesc.Raw = PREFIX
            .plus("&#18dbd1Добавлено %amount%x %item%!")
            .toRaw(),
        @SerialName("rick_morty_success")
        private val rickMortySuccess: StringDesc.Raw = PREFIX
            .plus("&#18dbd1Получен ответ: %result%")
            .toRaw(),
        @SerialName("rick_morty_fail")
        private val rickMortyFail: StringDesc.Raw = PREFIX
            .plus("&#db2c18Ошибка: %error%")
            .toRaw()
    ) {
        fun damaged(player: String) = damaged.replace("%player%", player)
        fun addItemSuccess(amount: Int, item: String) = addItemSuccess
            .replace("%amount%", amount.toString())
            .replace("%item%", item)

        fun rickMortySuccess(result: String) = rickMortySuccess.replace("%result%", result)
        fun rickMortyFail(error: String) = rickMortyFail.replace("%error%", error)
    }

    companion object {
        private val PREFIX = StringDesc.Raw("&7[&#DBB72BTEMPLATE&7] ")
    }
}
