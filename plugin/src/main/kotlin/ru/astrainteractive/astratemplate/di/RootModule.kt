package ru.astrainteractive.astratemplate.di

import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.core.di.CoreModule
import ru.astrainteractive.klibs.kdi.Module

interface RootModule : Module {
    val bukkitModule: BukkitModule

    val apiLocalModule: ApiLocalModule

    val apiRemoteModule: ApiRemoteModule

    val coreModule: CoreModule
}
