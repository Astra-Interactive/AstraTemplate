package ru.astrainteractive.astratemplate.command.damage

import org.bukkit.entity.Player

internal interface DamageCommand {
    class Result(
        val player: Player,
        val damage: Double,
        val damagerName: String
    )
}
