package ru.astrainteractive.astratemplate.command.core

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack

interface Command {
    val alias: String
    fun onCommand(commandContext: CommandContext<CommandSourceStack>)
}
