@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di

import org.bukkit.plugin.Plugin
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.events.EventListener
import ru.astrainteractive.astralibs.events.GlobalEventListener
import ru.astrainteractive.astratemplate.events.di.EventModule
import ru.astrainteractive.astratemplate.plugin.Translation

internal object EventModuleImpl : EventModule {
    override val eventListener: Dependency<EventListener> = Single { GlobalEventListener }
    override val plugin: Dependency<Plugin> = RootModule.plugin
    override val translation: Dependency<Translation> = RootModule.translationModule
}
