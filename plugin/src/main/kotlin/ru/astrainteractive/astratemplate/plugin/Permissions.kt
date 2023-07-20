package ru.astrainteractive.astratemplate.plugin

import ru.astrainteractive.astralibs.utils.Permission

/**
 * Permissions sealed itnerface
 */
sealed class Permissions(override val value: String) : Permission {
    data object Reload : Permissions("astra_template.reload")
    data object Damage : Permissions("astra_template.damage")
}
