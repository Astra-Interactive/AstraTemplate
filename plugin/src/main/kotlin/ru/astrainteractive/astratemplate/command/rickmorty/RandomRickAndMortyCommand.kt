package ru.astrainteractive.astratemplate.command.rickmorty

import org.bukkit.command.CommandSender
import ru.astrainteractive.astralibs.command.api.Command

interface RandomRickAndMortyCommand : Command<RandomRickAndMortyCommand.Input, RandomRickAndMortyCommand.Input> {
    class Input(val sender: CommandSender)
}
