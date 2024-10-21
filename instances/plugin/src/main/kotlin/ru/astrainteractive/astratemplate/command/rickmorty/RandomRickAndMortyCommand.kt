package ru.astrainteractive.astratemplate.command.rickmorty

import org.bukkit.command.CommandSender

internal interface RandomRickAndMortyCommand {
    class Result(val sender: CommandSender)
}
