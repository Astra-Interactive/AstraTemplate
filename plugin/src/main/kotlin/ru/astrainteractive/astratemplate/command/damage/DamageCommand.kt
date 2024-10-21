package ru.astrainteractive.astratemplate.command.damage

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.exception.CommandException

interface DamageCommand {
    class Result(
        val player: Player,
        val damage: Double,
        val damagerName: String
    )

    sealed class Error(message: String) : CommandException(message) {
        data object NoOp : Error("NoOp")
        data object NoPermission : Error("NoPermission")
        data object PlayerNotExists : Error("PlayerNotExists")
    }
}
