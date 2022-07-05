package com.astrainteractive.astratemplate.commands

import com.astrainteractive.astralibs.AstraLibs
import com.astrainteractive.astralibs.registerCommand
import com.astrainteractive.astralibs.registerTabCompleter
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

        /**
         * Return true if player has permission and null if not
         */
        fun noPermission(perm: String, block: DSLCommandBuilder.() -> Unit): Boolean? {
            if (!sender.hasPermission(perm)) {
                block.invoke(this)
                return null
            }
            return true
        }
    }

    data class DSLCommand(val commandArguments: List<CommandArgument>)

    data class CommandArgument(val content: String, val type: String, val commandArgument: CommandArgument?)

    fun command(alias: String, block: DSLCommandBuilder.() -> Unit) =
        AstraLibs.registerCommand(alias) { sender, args ->
            val astraDSLCommandData = AstraDSLCommandData(sender, args)
            AstraDSLCommand.dslCommand(astraDSLCommandData) {
                block(this)
            }
        }
    sealed class ArgumentStatus {
        /**
         * On argument empty
         */
        object Empty : ArgumentStatus()

        /**
         * When user put wrong params, ex: String on Int
         */
        object WrongParam : ArgumentStatus()

        /**
         * When result not found. ex: Player put player's name, but no player found
         */
        object ResultNotFound : ArgumentStatus()

        object Success : ArgumentStatus()

    }
    class ArgumentResult(private val status: ArgumentStatus) {
        fun onEmptyArgument(block: () -> Unit) {
            if (status == ArgumentStatus.Empty)
                block()
        }

        fun onWrongArgument(block: () -> Unit) {
            if (status == ArgumentStatus.WrongParam)
                block()
        }

        fun onResultNotFound(block: () -> Unit) {
            if (status == ArgumentStatus.ResultNotFound)
                block()
        }

        fun onFailure(block: () -> Unit) {
            if (status !is ArgumentStatus.Success)
                block()
        }
    }
    class AstraTabCompleter(val sender: CommandSender, val args: Array<out String>) {
        val size: Int
            get() = args.size
        val nowTyping: String?
            get() = args.lastOrNull()

        inline fun <T> onType(index: Int, block: (String?) -> T): T? {
            if (size - 1 == index)
                return block(nowTyping)
            return null
        }
    }

    fun onTabComplete(alias: String, block: AstraTabCompleter.() -> List<String>) {
        AstraLibs.registerTabCompleter(alias) { sender, args ->
            val astraTabCompleter = AstraTabCompleter(sender, args)
            return@registerTabCompleter block(astraTabCompleter)
        }
    }
}

