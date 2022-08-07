package com.astrainteractive.astratemplate.commands

import CommandManager
import com.astrainteractive.astralibs.async.AsyncHelper
import com.astrainteractive.astralibs.commands.AstraDSLCommand
import com.astrainteractive.astralibs.utils.Injector.inject
import com.astrainteractive.astratemplate.AstraTemplate
import com.astrainteractive.astratemplate.api.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun CommandManager.RandomRickAndMortyCharacter() = AstraDSLCommand.command("rickandmorty") {
    sender.sendMessage("Working on that...")
    AsyncHelper.launch(Dispatchers.IO) {
        inject<Repository>()?.getRandomCharacter()?.let {
            sender.sendMessage("Got response: ${it}")
        }
    }

}
