package ru.astrainteractive.astratemplate.command.api

import com.velocitypowered.api.proxy.ProxyServer
import ru.astrainteractive.astralibs.command.api.registry.CommandRegistryContext
import ru.astrainteractive.astratemplate.AstraTemplate

class VelocityCommandRegistryContext(
    val proxyServer: ProxyServer,
    val plugin: AstraTemplate
) : CommandRegistryContext
