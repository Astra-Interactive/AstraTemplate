package ru.astrainteractive.astratemplate.command.di

import ru.astrainteractive.astralibs.command.registrar.ForgeCommandRegistrarContext
import ru.astrainteractive.astralibs.lifecycle.Lifecycle
import ru.astrainteractive.astratemplate.command.helloworld.HelloCommandRegistrar

class CommandModule(
    private val commandRegistrarContext: ForgeCommandRegistrarContext
) {
    private val nodes = buildList {
        HelloCommandRegistrar().createNode().run(::add)
    }

    val lifecycle: Lifecycle = Lifecycle.Lambda(
        onEnable = {
            nodes.onEach(commandRegistrarContext::registerWhenReady)
        }
    )
}
