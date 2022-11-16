package ru.astrainteractive.astratemplate.command

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource


object HelloWorldCommand : ICommand("helloworld") {


    override fun onCommand(context: CommandContext<ServerCommandSource>): Int {
        println("Hello world!")
        return 1
    }

}