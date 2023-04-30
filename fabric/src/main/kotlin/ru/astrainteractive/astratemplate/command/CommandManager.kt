package ru.astrainteractive.astratemplate.command

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import ru.astrainteractive.astratemplate.di.RootModule

object CommandManager {
    private val commandComponent = CommandComponent(RootModule)
    private val commands = listOf(
        commandComponent.helloWorldCommand,
        commandComponent.rickMortyCommand,
        commandComponent.insertUserCommand,
        commandComponent.getAllUsersCommand
    )

    fun enable() {
        commands.forEach { CommandRegistrationCallback.EVENT.register(it::register) }
    }
}
