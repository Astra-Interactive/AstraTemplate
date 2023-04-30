package ru.astrainteractive.astratemplate.di.impl

import ru.astrainteractive.astralibs.Lateinit
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.DefaultBukkitDispatchers
import ru.astrainteractive.astralibs.logging.Logger
import ru.astrainteractive.astralibs.utils.buildWithSpigot
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.di.PluginModule

internal object PluginModuleImpl : PluginModule {
    override val plugin = Lateinit<AstraTemplate>()
    override val logger = Single {
        Logger.buildWithSpigot("AstraTemplate", plugin.value)
    }
    override val bukkitDispatchers = Single {
        DefaultBukkitDispatchers(plugin.value)
    }
    override val pluginScope = Single {
        object : AsyncComponent() {} as AsyncComponent
    }
}
