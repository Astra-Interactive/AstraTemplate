package ru.astrainteractive.astratemplate.command.api

import com.velocitypowered.api.proxy.ProxyServer
import ru.astrainteractive.astralibs.command.api.context.CommandContext
import ru.astrainteractive.astratemplate.AstraTemplate

class VelocityCommandRegistryContext(
    val proxyServer: ProxyServer,
    val plugin: AstraTemplate
) : CommandContext
