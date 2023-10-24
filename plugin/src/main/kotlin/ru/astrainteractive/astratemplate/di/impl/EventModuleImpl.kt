@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di.impl

import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.event.GlobalEventListener
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.event.di.EventModule
import ru.astrainteractive.klibs.kdi.Single
import ru.astrainteractive.klibs.kdi.getValue

internal class EventModuleImpl(
    rootModule: RootModule
) : EventModule {

    override val eventListener by Single { GlobalEventListener }
    override val plugin by rootModule.bukkitModule.plugin
    override val translation by rootModule.bukkitModule.translation
}
