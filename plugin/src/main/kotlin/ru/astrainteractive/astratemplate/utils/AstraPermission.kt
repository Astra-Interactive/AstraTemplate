package ru.astrainteractive.astratemplate.utils

import ru.astrainteractive.astralibs.utils.IPermission

sealed class AstraPermission(override val value: String): IPermission {
    object Reload : AstraPermission("astra_template.reload")
    object Damage : AstraPermission("astra_template.damage")
}