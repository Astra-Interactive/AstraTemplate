@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di.impl

import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.events.GlobalEventListener
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

internal class EventModuleImpl(
    pluginModule: RootModule
) : EventModule {

    override val eventListener by Single { GlobalEventListener }
    override val plugin by pluginModule.plugin
    override val translation by RootModuleImpl.translationModule
}
