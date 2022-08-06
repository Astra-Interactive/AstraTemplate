package com.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astralibs.async.AsyncHelper
import com.astrainteractive.astralibs.commands.AstraDSLCommand
import com.astrainteractive.astratemplate.AstraTemplate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CommandManager.RandomRickAndMortyCharacter() = AstraDSLCommand.command("rickandmorty") {
    sender.sendMessage("Working on that...")
    AsyncHelper.launch(Dispatchers.IO) {
        AstraTemplate.api.getRandomCharacter().await()?.let {
            sender.sendMessage("Got response: ${it}")
        }
    }

}