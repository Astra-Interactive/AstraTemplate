package ru.astrainteractive.astratemplate.di

import com.google.inject.Injector
import com.velocitypowered.api.proxy.ProxyServer
import org.slf4j.Logger
import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Module
import java.nio.file.Path

interface VelocityModule : Module {
    val injector: Dependency<Injector>
    val server: Dependency<ProxyServer>
    val logger: Dependency<Logger>
    val dataDirectory: Dependency<Path>
}
