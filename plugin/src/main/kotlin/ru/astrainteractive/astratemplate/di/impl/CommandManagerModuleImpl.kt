@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di.impl

import com.astrainteractive.astratemplate.api.remote.RickMortyApi
import org.bukkit.entity.Player
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.Provider
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule
import ru.astrainteractive.astratemplate.di.PluginModule
import ru.astrainteractive.astratemplate.di.RootModule
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.gui.di.SampleGuiModule
import ru.astrainteractive.astratemplate.plugin.Translation
import kotlin.random.Random

internal object CommandManagerModuleImpl : CommandManagerModule {
    private val rootModule: RootModule by RootModuleImpl
    private val pluginModule: PluginModule by PluginModuleImpl
    private val sampleGuiModule: SampleGuiModule by SampleGuiModuleImpl

    override val plugin: Dependency<AstraTemplate> = pluginModule.plugin
    override val translationModule: Dependency<Translation> = rootModule.translationModule
    override val rmApiModule: Dependency<RickMortyApi> = rootModule.rmApiModule
    override val pluginScope: Dependency<AsyncComponent> = pluginModule.pluginScope
    override val dispatchers: Dependency<BukkitDispatchers> = pluginModule.bukkitDispatchers
    override val randomIntProvider: Provider<Int> = Provider { Random.nextInt(1, 100) }
    override fun sampleGuiFactory(player: Player): Factory<SampleGUI> = Factory {
        SampleGUI(
            player = player,
            module = sampleGuiModule
        )
    }
}
