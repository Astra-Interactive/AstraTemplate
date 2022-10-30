package com.astrainteractive.astratemplate.modules

import com.astrainteractive.astratemplate.utils.PluginConfig
import ru.astrainteractive.astralibs.di.IReloadable

object PluginConfigProvider:IReloadable<PluginConfig>() {
    override fun initializer(): PluginConfig {
        return PluginConfig.kotlinxSerializaion()
//        return PluginConfig.create()
    }
}