package ru.astrainteractive.astratemplate.command

import ru.astrainteractive.astratemplate.command.core.Command
import ru.astrainteractive.astratemplate.command.core.DslCommand

class HelloWorldCommand : Command by DslCommand(
    alias = "helloworld",
    block = {
        println("Hello from HelloWorldCommand!")
    }
)
