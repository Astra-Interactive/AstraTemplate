package ru.astrainteractive.astratemplate.command.core

import com.mojang.brigadier.CommandDispatcher
import net.minecraft.commands.CommandSourceStack
import net.minecraft.commands.Commands

class DefaultCommandRegistry(
    private val dispatcher: CommandDispatcher<CommandSourceStack>
) : CommandRegistry {

    override fun register(command: Command) {
        dispatcher.register(
            Commands.literal(command.alias).executes { commandContext ->
                command.onCommand(commandContext)
                com.mojang.brigadier.Command.SINGLE_SUCCESS
            }
        )
    }
}
