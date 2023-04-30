@file:OptIn(UnsafeApi::class)

package ru.astrainteractive.astratemplate.di

import com.astrainteractive.astratemplate.api.remote.RickMortyApi
import org.bukkit.entity.Player
import org.jetbrains.kotlin.tooling.core.UnsafeApi
import ru.astrainteractive.astralibs.Dependency
import ru.astrainteractive.astralibs.Factory
import ru.astrainteractive.astralibs.Single
import ru.astrainteractive.astralibs.async.AsyncComponent
import ru.astrainteractive.astralibs.async.BukkitDispatchers
import ru.astrainteractive.astralibs.getValue
import ru.astrainteractive.astratemplate.AstraTemplate
import ru.astrainteractive.astratemplate.api.ItemStackSpigotAPI
import ru.astrainteractive.astratemplate.commands.di.CommandManagerModule
import ru.astrainteractive.astratemplate.gui.SampleGUI
import ru.astrainteractive.astratemplate.gui.SampleGUIViewModel
import ru.astrainteractive.astratemplate.plugin.Translation

internal object CommandManagerModuleImpl : CommandManagerModule {
    override val plugin: Dependency<AstraTemplate> = RootModule.plugin
    override val translationModule: Dependency<Translation> = RootModule.translationModule
    override val rmApiModule: Dependency<RickMortyApi> = RootModule.rmApiModule
    override val pluginScope: Single<AsyncComponent> = RootModule.pluginScope
    override val dispatchers: Single<BukkitDispatchers> = RootModule.bukkitDispatchers

    override fun sampleGuiFactory(player: Player): Factory<SampleGUI> = Factory {
        val sampleGuiViewModelFactory = Factory {
            val localApi by RootModule.localApiModule
            SampleGUIViewModel(localApi, ItemStackSpigotAPI)
        }
        SampleGUI(
            player = player,
            translationModule = RootModule.translationModule,
            viewModel = sampleGuiViewModelFactory.build(),
            dispatchers = RootModule.bukkitDispatchers.value
        )
    }
}
