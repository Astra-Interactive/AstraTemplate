package ru.astrainteractive.astratemplate.di

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Module
import java.nio.file.Path

interface VelocityModule : Module {
    val injector: Lateinit<Injector>
    val server: Lateinit<ProxyServer>
    val logger: Lateinit<Logger>
    val dataDirectory: Lateinit<Path>
}
