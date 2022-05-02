package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerCommand
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

object AstraDSLCommand {
    class AstraDSLCommandData(val commandSender: CommandSender, val args: Array<out String>)

    @DslMarker
    annotation class CommandDSL

    fun dslCommand(astraDSLCommandData: AstraDSLCommandData, block: DSLCommandBuilder.() -> Unit): DSLCommand =
        DSLCommandBuilder(astraDSLCommandData).apply(block).build()

    @CommandDSL
    class DSLCommandBuilder(private val astraDSLCommandData: AstraDSLCommandData) {
        val sentByPlayer: Boolean
            get() = sender is Player
        val sentByConsole: Boolean
            get() = sender is ConsoleCommandSender

        val sender: CommandSender
            get() = astraDSLCommandData.commandSender
        private var commandArguments: ArrayList<CommandArgument> = ArrayList()

        fun build(): DSLCommand = DSLCommand(commandArguments)
        fun getArgumentOrNull(i: Int): String? = astraDSLCommandData.args.getOrNull(i)
    }

    data class DSLCommand(val commandArguments: List<CommandArgument>)

    data class CommandArgument(val content: String, val type: String, val commandArgument: CommandArgument?)

    fun command(alias: String, block: (AstraDSLCommand.AstraDSLCommandData) -> Unit) =
        AstraLibs.registerCommand(alias) { sender, args ->
            block(AstraDSLCommand.AstraDSLCommandData(sender, args))
        }
}

