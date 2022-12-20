package ru.astrainteractive.astratemplate.utils

import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

interface IPermission {
    val value: String
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