package ru.astrainteractive.astratemplate.command.core

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource

interface Command {
    val alias: String
    fun onCommand(commandContext: CommandContext<ServerCommandSource>): Int
}
