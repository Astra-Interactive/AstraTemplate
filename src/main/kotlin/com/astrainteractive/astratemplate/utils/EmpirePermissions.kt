package com.astrainteractive.astratemplate.utils

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

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

sealed class AstraPermission(val value: String) {
    object Reload : AstraPermission("astra_template.reload")
    object Damage : AstraPermission("astra_template.damage")
    fun hasPermission(player: CommandSender) = player.hasPermission(value)

    /**
     * If has astra_template.damage.2 => returns 2
     */
    fun permissionSize(player: Player) = player.effectivePermissions
        .firstOrNull { it.permission.startsWith(value) }
        ?.permission
        ?.replace("$value.", "")
        ?.toIntOrNull()
}