package ru.astrainteractive.astratemplate.command.api

import com.velocitypowered.api.command.CommandSource
import ru.astrainteractive.astralibs.command.api.context.CommandContext

class VelocityCommandContext(
    val alias: String,
    val source: CommandSource,
    val arguments: Array<String>
) : CommandContext
