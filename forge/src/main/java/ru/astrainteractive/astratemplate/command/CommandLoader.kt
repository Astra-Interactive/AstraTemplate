package ru.astrainteractive.astratemplate.command

import net.minecraftforge.event.RegisterCommandsEvent
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astratemplate.command.core.DefaultCommandRegistry
import ru.astrainteractive.astratemplate.command.helloworld.HelloWorldCommand

class CommandLoader(private val logger: Logger) {

    fun registerCommands(event: RegisterCommandsEvent) {
        logger.info("CommandLoader", "registerCommands")
        val registry = DefaultCommandRegistry(event.dispatcher)
        registry.register(HelloWorldCommand())
    }
}
