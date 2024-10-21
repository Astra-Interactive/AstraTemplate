package ru.astrainteractive.astratemplate.di.impl

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.VelocityModule
import java.nio.file.Path

class VelocityModuleImpl : VelocityModule {
    override lateinit var injector: Injector
    override lateinit var server: ProxyServer
    override lateinit var plugin: AstraTemplate
    override lateinit var logger: Logger
    override lateinit var dataDirectory: Path
}
