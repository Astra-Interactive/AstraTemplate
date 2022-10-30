package ru.astrainteractive.astratemplate.modules

import ru.astrainteractive.astralibs.EmpireSerializer
import ru.astrainteractive.astralibs.di.IReloadable
import ru.astrainteractive.astralibs.di.reloadable
import ru.astrainteractive.astratemplate.utils.Files
import ru.astrainteractive.astratemplate.utils.PluginConfig

/**
 * There's two different ways to instantiate a reloadable module
 */
//val PluginConfigProvider = reloadable {
//    EmpireSerializer.toClass<PluginConfig>(Files.configFile) ?: PluginConfig()
//}
object PluginConfigProvider:IReloadable<PluginConfig>() {
    override fun initializer(): PluginConfig {
        return EmpireSerializer.toClass<PluginConfig>(Files.configFile) ?: PluginConfig()
    }
}