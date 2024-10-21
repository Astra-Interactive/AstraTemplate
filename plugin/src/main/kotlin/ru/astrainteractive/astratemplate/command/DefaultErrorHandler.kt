package ru.astrainteractive.astratemplate.command

import ru.astrainteractive.astralibs.command.api.context.BukkitCommandContext
import ru.astrainteractive.astralibs.command.api.error.ErrorHandler

class DefaultErrorHandler : ErrorHandler<BukkitCommandContext> {
    override fun handle(commandContext: BukkitCommandContext, throwable: Throwable) {
        when (throwable) {
            else -> Unit
        }
    }
}
