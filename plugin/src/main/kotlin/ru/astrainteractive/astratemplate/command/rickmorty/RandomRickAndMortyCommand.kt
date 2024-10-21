package ru.astrainteractive.astratemplate.command.rickmorty

import org.bukkit.command.CommandSender

interface RandomRickAndMortyCommand {
    class Result(val sender: CommandSender)
}
