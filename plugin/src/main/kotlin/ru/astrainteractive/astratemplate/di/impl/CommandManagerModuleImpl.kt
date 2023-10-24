@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di.impl

import org.bukkit.entity.Player
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astratemplate.command.di.CommandManagerModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.gui.di.SampleGuiModule
import ru.astrainteractive.klibs.kdi.Factory
import ru.astrainteractive.klibs.kdi.Provider
import ru.astrainteractive.klibs.kdi.getValue
import kotlin.random.Random

internal class CommandManagerModuleImpl(
    rootModule: RootModule
) : CommandManagerModule {

    override val plugin by rootModule.servicesModule.plugin
    override val translation by rootModule.servicesModule.translation
    override val rmApi = rootModule.apiRemoteModule.rickMortyApi
    override val pluginScope by rootModule.servicesModule.pluginScope
    override val dispatchers by rootModule.servicesModule.bukkitDispatchers
    override val randomIntProvider: Provider<Int> = Provider { Random.nextInt(1, 100) }
    private val sampleGuiModule by Provider {
        SampleGuiModuleImpl(rootModule)
    }

    override fun sampleGuiFactory(player: Player): Factory<SampleGUI> = Factory {
        val sampleGuiModule: SampleGuiModule by sampleGuiModule
        SampleGUI(
            player = player,
            module = sampleGuiModule
        )
    }
}
