package ru.astrainteractive.astratemplate.event.di

import org.bukkit.plugin.Plugin
import ru.astrainteractive.astralibs.event.EventListener
import ru.astrainteractive.astralibs.string.BukkitTranslationContext
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.shared.core.PluginTranslation
import ru.astrainteractive.klibs.kdi.getValue

interface EventDependencies {
    val eventListener: EventListener
    val plugin: Plugin
    val translation: PluginTranslation
    val bukkitTranslationContext: BukkitTranslationContext

    class Default(rootModule: RootModule) : EventDependencies {
        override val eventListener by rootModule.bukkitModule.eventListener
        override val plugin by rootModule.bukkitModule.plugin
        override val translation by rootModule.coreModule.translation
        override val bukkitTranslationContext by rootModule.bukkitModule.bukkitTranslationContext
    }
}
