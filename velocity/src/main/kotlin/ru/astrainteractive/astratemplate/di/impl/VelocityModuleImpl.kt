package ru.astrainteractive.astratemplate.di.impl

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.VelocityModule
import ru.astrainteractive.klibs.kdi.Lateinit
import java.nio.file.Path

class VelocityModuleImpl : VelocityModule {
    override val injector = Lateinit<Injector>()
    override val server = Lateinit<ProxyServer>()
    override val plugin: Lateinit<AstraTemplate> = Lateinit()
    override val logger = Lateinit<Logger>()
    override val dataDirectory = Lateinit<Path>()
}
