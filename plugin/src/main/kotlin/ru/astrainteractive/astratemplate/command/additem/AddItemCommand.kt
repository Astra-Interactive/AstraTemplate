package ru.astrainteractive.astratemplate.command.additem

import org.bukkit.Material
import org.bukkit.entity.Player
import ru.astrainteractive.astralibs.command.api.command.BukkitCommand

interface AddItemCommand : BukkitCommand {
    sealed interface Result {
        data object NoEx : Result
        data object SenderNotPlayer : Result
        data object PlayerNotExists : Result
        data object ItemNotfound : Result
        class Success(
            val player: Player,
            val amount: Int,
            val item: Material
        ) : Result
    }

    class Input(
        val player: Player,
        val amount: Int,
        val item: Material
    )
}
