package ru.astrainteractive.astratemplate.di

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astratemplate.AstraTemplate
import java.nio.file.Path

interface VelocityModule {
    val injector: Injector
    val server: ProxyServer
    val plugin: AstraTemplate
    val logger: Logger
    val dataDirectory: Path
}
