package ru.astrainteractive.astratemplate.shared.core

import ru.astrainteractive.astralibs.permission.Permission


/**
 * Permissions sealed itnerface
 */
sealed class Permissions(override val value: String) : Permission {
    data object Reload : Permissions("astra_template.reload")
    data object Damage : Permissions("astra_template.damage")
}
