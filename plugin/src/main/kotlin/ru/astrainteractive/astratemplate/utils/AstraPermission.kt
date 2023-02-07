package ru.astrainteractive.astratemplate.utils

import ru.astrainteractive.astralibs.utils.Permission

sealed class AstraPermission(override val value: String) : Permission {
    object Reload : AstraPermission("astra_template.reload")
    object Damage : AstraPermission("astra_template.damage")
}
