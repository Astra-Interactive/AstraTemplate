package com.astrainteractive.astratemplate.utils

/**
 * Permission class.
 *
 * All permission should be stored in companion object
 */
public object EmpirePermissions {
    public val reload: String
        get() = "astra_template.reload"
    public val damage: String
        get() = "astra_template.damage"
}
