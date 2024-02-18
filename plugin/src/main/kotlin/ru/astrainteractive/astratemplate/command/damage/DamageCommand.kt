package ru.astrainteractive.astratemplate.command.damage

import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.command.BukkitCommand

interface DamageCommand : BukkitCommand {
    sealed interface Result {
        data object NoOp : Result
        data object NoPermission : Result
        data object PlayerNotExists : Result
        class Success(val player: Player, val damage: Double, val damagerName: String) : Result
    }

    class Input(val player: Player, val damage: Double, val damagerName: String)
}
