package ru.astrainteractive.astratemplate.command

import net.minecraftforge.event.RegisterCommandsEvent
import ru.astrainteractive.astralibs.logging.JUtiltLogger
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.command.core.DefaultCommandRegistry
import ru.astrainteractive.astratemplate.command.helloworld.HelloWorldCommand

class CommandLoader : Logger by JUtiltLogger("CommandLoader") {

    fun registerCommands(event: RegisterCommandsEvent) {
        info { "#registerCommands" }
        val registry = DefaultCommandRegistry(event.dispatcher)
        registry.register(HelloWorldCommand())
    }
}
