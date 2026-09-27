@file:Suppress("LongParameterList")

package ru.astrainteractive.astratemplate.core.plugin

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.astrainteractive.astralibs.localization.component.LocalizableComponent
import ru.astrainteractive.astralibs.localization.component.PlaceholderReplacement
import ru.astrainteractive.astralibs.localization.component.replace
import ru.astrainteractive.astralibs.localization.component.replaceAll
import ru.astrainteractive.astralibs.localization.locale.MinecraftLocales
import ru.astrainteractive.astralibs.localization.text.LocalizedText

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
        val noPermission: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18У вас нет прав!")
                translation(MinecraftLocales.EN_US, "&#db2c18You don't have permission!")
            }
        ),
        @SerialName("not_player")
        val notPlayer: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Вы не игрок")
                translation(MinecraftLocales.EN_US, "&#db2c18You are not a player")
            }
        ),
        @SerialName("player_not_exists")
        val playerNotExists: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Игрока нет!")
                translation(MinecraftLocales.EN_US, "&#db2c18Player does not exist!")
            }
        ),
        @SerialName("item_not_found")
        val itemNotFound: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Предмет не найден")
                translation(MinecraftLocales.EN_US, "&#db2c18Item not found")
            }
        ),
    )

    @Serializable
    class Database(
        @SerialName("success")
        val dbSuccess: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Успешно подключено к базе данных")
                translation(MinecraftLocales.EN_US, "&#18dbd1Connected to the database")
            }
        ),
        @SerialName("fail")
        val dbFail: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Нет подключения к базе данных")
                translation(MinecraftLocales.EN_US, "&#db2c18No connection to the database")
            }
        ),
    )

    @Serializable
    class General(
        @SerialName("reload")
        val reload: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#dbbb18Перезагрузка плагина")
                translation(MinecraftLocales.EN_US, "&#dbbb18Reloading the plugin")
            }
        ),
        @SerialName("reload_complete")
        val reloadComplete: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#42f596Перезагрузка успешно завершена")
                translation(MinecraftLocales.EN_US, "&#42f596Reload complete")
            }
        ),
        @SerialName("getByByCheck")
        val getByByCheck: LocalizedText = PREFIX.concat(LocalizedText.shared("&#db2c18getByByCheck"))
    )

    @Serializable
    class Menu(
        @SerialName("title")
        val menuTitle: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Меню")
            translation(MinecraftLocales.EN_US, "&#18dbd1Menu")
        },
        @SerialName("add_player")
        val menuAddPlayer: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Добавить игрока")
            translation(MinecraftLocales.EN_US, "&#18dbd1Add player")
        },
        @SerialName("first_page")
        val menuFirstPage: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#dbbb18Вы на первой странице")
            translation(MinecraftLocales.EN_US, "&#dbbb18You are on the first page")
        },
        @SerialName("last_page")
        val menuLastPage: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#dbbb18Вы на последней странице")
            translation(MinecraftLocales.EN_US, "&#dbbb18You are on the last page")
        },
        @SerialName("prev_page")
        val menuPrevPage: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Пред. страницы")
            translation(MinecraftLocales.EN_US, "&#18dbd1Previous page")
        },
        @SerialName("next_page")
        val menuNextPage: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1След. страница")
            translation(MinecraftLocales.EN_US, "&#18dbd1Next page")
        },
        @SerialName("back")
        val menuBack: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Назад")
            translation(MinecraftLocales.EN_US, "&#18dbd1Back")
        },
        @SerialName("close")
        val menuClose: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Закрыть)")
            translation(MinecraftLocales.EN_US, "&#18dbd1Close")
        }
    )

    @Serializable
    class Custom(
        @SerialName("block_placed")
        val blockPlaced: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Блок поставлен!")
                translation(MinecraftLocales.EN_US, "&#18dbd1Block placed!")
            }
        ),
        @SerialName("no_player_name")
        val noPlayerName: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Вы не ввели имя игрока!")
                translation(MinecraftLocales.EN_US, "&#db2c18You did not enter a player name!")
            }
        ),
        @SerialName("damaged")
        private val damaged: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Вас продамажил игрок %player%!")
                translation(MinecraftLocales.EN_US, "&#db2c18You were damaged by %player%!")
            }
        ),
        @SerialName("damage_hint")
        val damageHint: LocalizedText = LocalizedText.shared("<amount>"),
        @SerialName("add_item_success")
        private val addItemSuccess: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Добавлено %amount%x %item%!")
                translation(MinecraftLocales.EN_US, "&#18dbd1Added %amount%x %item%!")
            }
        ),
        @SerialName("rick_morty_success")
        private val rickMortySuccess: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Получен ответ: %result%")
                translation(MinecraftLocales.EN_US, "&#18dbd1Got a response: %result%")
            }
        ),
        @SerialName("rick_morty_fail")
        private val rickMortyFail: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Ошибка: %error%")
                translation(MinecraftLocales.EN_US, "&#db2c18Error: %error%")
            }
        )
    ) {
        fun damaged(player: String): LocalizableComponent = damaged.replace("%player%", player)

        fun addItemSuccess(amount: Int, item: String): LocalizableComponent = addItemSuccess.replaceAll(
            PlaceholderReplacement.plain("%amount%", amount.toString()),
            PlaceholderReplacement.plain("%item%", item)
        )

        fun rickMortySuccess(result: String): LocalizableComponent = rickMortySuccess.replace("%result%", result)

        fun rickMortyFail(error: String): LocalizableComponent = rickMortyFail.replace("%error%", error)
    }

    companion object {
        private val PREFIX = LocalizedText.shared("&7[&#DBB72BTEMPLATE&7] ")
    }
}
