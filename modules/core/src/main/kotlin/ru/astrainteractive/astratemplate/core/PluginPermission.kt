package ru.astrainteractive.astratemplate.core

import ru.astrainteractive.astralibs.permission.Permission

/**
 * Permissions sealed itnerface
 */
sealed class PluginPermission(override val value: String) : Permission {
    data object Reload : PluginPermission("astra_template.reload")
    data object Damage : PluginPermission("astra_template.damage")
}
