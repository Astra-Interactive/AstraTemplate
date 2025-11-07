package ru.astrainteractive.astratemplate.di

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astratemplate.AstraTemplate
import java.nio.file.Path

class VelocityModule {
    lateinit var injector: Injector
    lateinit var server: ProxyServer
    lateinit var plugin: AstraTemplate
    lateinit var logger: Logger
    lateinit var dataDirectory: Path
}
