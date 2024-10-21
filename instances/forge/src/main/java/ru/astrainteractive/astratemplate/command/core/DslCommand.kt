package ru.astrainteractive.astratemplate.command.core

import com.mojang.brigadier.context.CommandContext
import net.minecraft.commands.CommandSourceStack

class DslCommand(
    override val alias: String,
    private val block: (commandContext: CommandContext<CommandSourceStack>) -> Unit
) : Command {
    override fun onCommand(commandContext: CommandContext<CommandSourceStack>) {
        block.invoke(commandContext)
    }
}
