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
 * Texts of the plugin, grouped by the feature that sends them. Every text has a default, so the plugin works
 * without `translation.yml` and a missing key keeps its default.
 */
@Serializable
data class PluginTranslation(
    @SerialName("command_error")
    val commandError: CommandError = CommandError(),
    @SerialName("reload")
    val reload: Reload = Reload(),
    @SerialName("translation_check")
    val translationCheck: TranslationCheck = TranslationCheck(),
    @SerialName("damage")
    val damage: Damage = Damage(),
    @SerialName("add_item")
    val addItem: AddItem = AddItem(),
    @SerialName("rick_morty")
    val rickMorty: RickMorty = RickMorty(),
    @SerialName("block_place")
    val blockPlace: BlockPlace = BlockPlace(),
    @SerialName("menu")
    val menu: Menu = Menu()
) {
    /** Failures any command can report. */
    @Serializable
    data class CommandError(
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
        )
    )

    @Serializable
    data class Reload(
        @SerialName("started")
        val started: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#dbbb18Перезагрузка плагина")
                translation(MinecraftLocales.EN_US, "&#dbbb18Reloading the plugin")
            }
        ),
        @SerialName("completed")
        val completed: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#42f596Перезагрузка успешно завершена")
                translation(MinecraftLocales.EN_US, "&#42f596Reload complete")
            }
        )
    )

    /** Sent by `/translation` to show which text a player gets in their language, e.g. after a reload. */
    @Serializable
    data class TranslationCheck(
        @SerialName("message")
        val message: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Этот текст показан на русском")
                translation(MinecraftLocales.EN_US, "&#18dbd1This text is shown in English")
            }
        )
    )

    @Serializable
    data class Damage(
        @SerialName("damaged_by")
        private val damagedBy: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Вас продамажил игрок %player%!")
                translation(MinecraftLocales.EN_US, "&#db2c18You were damaged by %player%!")
            }
        ),
        @SerialName("hint")
        val hint: LocalizedText = LocalizedText.shared("<amount>")
    ) {
        fun damagedBy(player: String): LocalizableComponent = damagedBy.replace("%player%", player)
    }

    @Serializable
    data class AddItem(
        @SerialName("success")
        private val success: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Добавлено %amount%x %item%!")
                translation(MinecraftLocales.EN_US, "&#18dbd1Added %amount%x %item%!")
            }
        ),
        @SerialName("item_not_found")
        val itemNotFound: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Предмет не найден")
                translation(MinecraftLocales.EN_US, "&#db2c18Item not found")
            }
        )
    ) {
        fun success(amount: Int, item: String): LocalizableComponent = success.replaceAll(
            PlaceholderReplacement.plain("%amount%", amount.toString()),
            PlaceholderReplacement.plain("%item%", item)
        )
    }

    @Serializable
    data class RickMorty(
        @SerialName("success")
        private val success: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Получен ответ: %result%")
                translation(MinecraftLocales.EN_US, "&#18dbd1Got a response: %result%")
            }
        ),
        @SerialName("failure")
        private val failure: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#db2c18Ошибка: %error%")
                translation(MinecraftLocales.EN_US, "&#db2c18Error: %error%")
            }
        )
    ) {
        fun success(result: String): LocalizableComponent = success.replace("%result%", result)

        fun failure(error: String): LocalizableComponent = failure.replace("%error%", error)
    }

    @Serializable
    data class BlockPlace(
        @SerialName("message")
        val message: LocalizedText = PREFIX.concat(
            LocalizedText.build {
                translation(MinecraftLocales.RU_RU, "&#18dbd1Блок поставлен!")
                translation(MinecraftLocales.EN_US, "&#18dbd1Block placed!")
            }
        )
    )

    @Serializable
    data class Menu(
        @SerialName("title")
        val title: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Меню")
            translation(MinecraftLocales.EN_US, "&#18dbd1Menu")
        },
        @SerialName("add_player")
        val addPlayer: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Добавить игрока")
            translation(MinecraftLocales.EN_US, "&#18dbd1Add player")
        },
        @SerialName("previous_page")
        val previousPage: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Пред. страница")
            translation(MinecraftLocales.EN_US, "&#18dbd1Previous page")
        },
        @SerialName("next_page")
        val nextPage: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1След. страница")
            translation(MinecraftLocales.EN_US, "&#18dbd1Next page")
        },
        @SerialName("back")
        val back: LocalizedText = LocalizedText.build {
            translation(MinecraftLocales.RU_RU, "&#18dbd1Назад")
            translation(MinecraftLocales.EN_US, "&#18dbd1Back")
        }
    )

    companion object {
        private val PREFIX = LocalizedText.shared("&7[&#DBB72BTEMPLATE&7] ")
    }
}
