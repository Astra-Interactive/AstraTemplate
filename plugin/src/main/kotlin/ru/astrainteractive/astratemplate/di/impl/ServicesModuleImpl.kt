package ru.astrainteractive.astratemplate.di.impl

import kotlinx.serialization.encodeToString
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.configloader.ConfigLoader
import ru.astrainteractive.astralibs.filemanager.impl.JVMFileManager
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.util.buildWithSpigot
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.ServicesModule
import ru.astrainteractive.astratemplate.di.factory.MainConfigurationFactory
import ru.astrainteractive.astratemplate.plugin.MainConfiguration
import ru.astrainteractive.astratemplate.plugin.Translation
import ru.astrainteractive.klibs.kdi.Lateinit
import ru.astrainteractive.klibs.kdi.Reloadable
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

class ServicesModuleImpl : ServicesModule {
    override val plugin = Lateinit<AstraTemplate>()
    override val configLoader: Single<ConfigLoader> = Single {
        ConfigLoader()
    }
    override val logger = Single {
        Logger.buildWithSpigot("AstraTemplate", plugin.value)
    }
    override val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }
    override val pluginScope = Single {
        object : AsyncComponent() {} as AsyncComponent
    }

    override val configurationModule = Reloadable {
        MainConfigurationFactory(
            plugin = plugin.value,
            configLoader = configLoader.value
        ).create()
    }

    override val translation = Reloadable {
        val plugin by plugin
        Translation(plugin)
    }
}