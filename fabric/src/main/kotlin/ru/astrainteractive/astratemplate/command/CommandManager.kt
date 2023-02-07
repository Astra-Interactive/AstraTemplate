package ru.astrainteractive.astratemplate.command

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback

object CommandManager {
    private val commands = listOf(
        HelloWorldCommand,
        RickMortyCommand,
        InsertUserCommand,
        GetAllUsersCommand
    )

    fun enable() {
        commands.forEach { CommandRegistrationCallback.EVENT.register(it::register) }
    }
}
