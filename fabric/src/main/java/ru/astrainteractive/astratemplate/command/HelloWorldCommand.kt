package ru.astrainteractive.astratemplate.command

import com.mojang.brigadier.context.CommandContext
import net.minecraft.server.command.ServerCommandSource
import ru.astrainteractive.astratemplate.HelloWorldModule


object HelloWorldCommand : ICommand("helloworld") {


    override fun onCommand(context: CommandContext<ServerCommandSource>): Int {
        println(HelloWorldModule.value)
        return 1
    }

}