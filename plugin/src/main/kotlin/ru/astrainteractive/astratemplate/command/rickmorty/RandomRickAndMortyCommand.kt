package ru.astrainteractive.astratemplate.command.rickmorty

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.command.BukkitCommand

interface RandomRickAndMortyCommand : BukkitCommand {
    class Input(val sender: CommandSender)
}
