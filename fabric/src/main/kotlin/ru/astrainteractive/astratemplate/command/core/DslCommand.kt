package ru.astrainteractive.astratemplate.command.core

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource

class DslCommand(
    override val alias: String,
    private val block: (commandContext: CommandContext<ServerCommandSource>) -> Unit
) : Command {
    override fun onCommand(commandContext: CommandContext<ServerCommandSource>): Int {
        block.invoke(commandContext)
        return 1
    }
}
