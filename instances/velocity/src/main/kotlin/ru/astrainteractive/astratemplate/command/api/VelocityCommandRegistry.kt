package ru.astrainteractive.astratemplate.command.api

import com.velocitypowered.api.command.SimpleCommand

fun <T : Any> VelocityCommandRegistryContext.registerCommand(
    alias: String,
    parse: (SimpleCommand.Invocation) -> Result<T>,
    onSuccess: (T) -> Unit,
    onFailure: (SimpleCommand.Invocation, Throwable) -> Unit = { _, _ -> }
) {
    val commandMeta = this.proxyServer.commandManager
        .metaBuilder(alias)
        .aliases(alias)
        .plugin(this.plugin)
        .build()
    val velocityCommand = SimpleCommand { ctx ->
        parse.invoke(ctx)
            .onFailure { throwable -> onFailure.invoke(ctx, throwable) }
            .onSuccess { result -> onSuccess.invoke(result) }
    }
    proxyServer.commandManager.register(commandMeta, velocityCommand)
}
