package ru.astrainteractive.astratemplate.plugin

import ru.astrainteractive.astralibs.utils.Permission

sealed class Permissions(override val value: String) : Permission {
    object Reload : Permissions("astra_template.reload")
    object Damage : Permissions("astra_template.damage")
}
