package ru.astrainteractive.astratemplate.command.helloworld

import ru.astrainteractive.astratemplate.command.core.Command
import ru.astrainteractive.astratemplate.command.core.DslCommand

class HelloWorldCommand : Command by DslCommand(
    alias = "helloworld",
    block = {
        println("Hello world")
    }
)
