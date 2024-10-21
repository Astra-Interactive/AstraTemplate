package ru.astrainteractive.astratemplate.event.di

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.kyori.KyoriComponentSerializer
import ru.astrainteractive.astratemplate.core.PluginTranslation
import ru.astrainteractive.astratemplate.core.util.getValue
import ru.astrainteractive.astratemplate.di.RootModule

internal interface EventDependencies {
    val eventListener: EventListener
    val plugin: Plugin
    val translation: PluginTranslation
    val kyoriComponentSerializer: KyoriComponentSerializer

    class Default(
        rootModule: RootModule,
        override val eventListener: EventListener
    ) : EventDependencies {
        override val plugin = rootModule.bukkitModule.plugin
        override val translation by rootModule.coreModule.translation
        override val kyoriComponentSerializer by rootModule.bukkitModule.kyoriComponentSerializer
    }
}
