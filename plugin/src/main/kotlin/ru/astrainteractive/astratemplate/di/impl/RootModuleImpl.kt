package ru.astrainteractive.astratemplate.di.impl

import CommandManager
import java.io.File
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astratemplate.api.local.di.ApiLocalModule
import ru.astrainteractive.astratemplate.api.remote.di.ApiRemoteModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.di.ServicesModule
import ru.astrainteractive.astratemplate.event.EventManager
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

internal class RootModuleImpl : RootModule {

    override val servicesModule: ServicesModule by Single {
        ServicesModuleImpl()
    }

    override val apiLocalModule: ApiLocalModule by Single {
        ApiLocalModule.Default("${servicesModule.plugin.value.dataFolder}${File.separator}data.db")
    }

    override val apiRemoteModule: ApiRemoteModule by Single {
        ApiRemoteModule.Default()
    }

    override val eventHandlerModule = Single {
        val eventModule = EventModuleImpl(this)
        EventManager(eventModule)
    }

    override val commandManager = Single {
        val commandManagerModule = CommandManagerModuleImpl(this)
        CommandManager(commandManagerModule)
    }
}
